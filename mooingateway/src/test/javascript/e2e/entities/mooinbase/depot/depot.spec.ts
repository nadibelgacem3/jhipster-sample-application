import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { DepotComponentsPage, DepotDeleteDialog, DepotUpdatePage } from './depot.page-object';

const expect = chai.expect;

describe('Depot e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let depotComponentsPage: DepotComponentsPage;
  let depotUpdatePage: DepotUpdatePage;
  let depotDeleteDialog: DepotDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Depots', async () => {
    await navBarPage.goToEntity('depot');
    depotComponentsPage = new DepotComponentsPage();
    await browser.wait(ec.visibilityOf(depotComponentsPage.title), 5000);
    expect(await depotComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseDepot.home.title');
    await browser.wait(ec.or(ec.visibilityOf(depotComponentsPage.entities), ec.visibilityOf(depotComponentsPage.noResult)), 1000);
  });

  it('should load create Depot page', async () => {
    await depotComponentsPage.clickOnCreateButton();
    depotUpdatePage = new DepotUpdatePage();
    expect(await depotUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseDepot.home.createOrEditLabel');
    await depotUpdatePage.cancel();
  });

  it('should create and save Depots', async () => {
    const nbButtonsBeforeCreate = await depotComponentsPage.countDeleteButtons();

    await depotComponentsPage.clickOnCreateButton();

    await promise.all([
      depotUpdatePage.setNameInput('name'),
      depotUpdatePage.setDetailsInput('details'),
      depotUpdatePage.setLocationInput('location'),
      depotUpdatePage.setCompanyIDInput('5'),
    ]);

    expect(await depotUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await depotUpdatePage.getDetailsInput()).to.eq('details', 'Expected Details value to be equals to details');
    expect(await depotUpdatePage.getLocationInput()).to.eq('location', 'Expected Location value to be equals to location');
    expect(await depotUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');

    await depotUpdatePage.save();
    expect(await depotUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await depotComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Depot', async () => {
    const nbButtonsBeforeDelete = await depotComponentsPage.countDeleteButtons();
    await depotComponentsPage.clickOnLastDeleteButton();

    depotDeleteDialog = new DepotDeleteDialog();
    expect(await depotDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseDepot.delete.question');
    await depotDeleteDialog.clickOnConfirmButton();

    expect(await depotComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
