import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { TVAComponentsPage, TVADeleteDialog, TVAUpdatePage } from './tva.page-object';

const expect = chai.expect;

describe('TVA e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tVAComponentsPage: TVAComponentsPage;
  let tVAUpdatePage: TVAUpdatePage;
  let tVADeleteDialog: TVADeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TVAS', async () => {
    await navBarPage.goToEntity('tva');
    tVAComponentsPage = new TVAComponentsPage();
    await browser.wait(ec.visibilityOf(tVAComponentsPage.title), 5000);
    expect(await tVAComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesTVa.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tVAComponentsPage.entities), ec.visibilityOf(tVAComponentsPage.noResult)), 1000);
  });

  it('should load create TVA page', async () => {
    await tVAComponentsPage.clickOnCreateButton();
    tVAUpdatePage = new TVAUpdatePage();
    expect(await tVAUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincompaniesTVa.home.createOrEditLabel');
    await tVAUpdatePage.cancel();
  });

  it('should create and save TVAS', async () => {
    const nbButtonsBeforeCreate = await tVAComponentsPage.countDeleteButtons();

    await tVAComponentsPage.clickOnCreateButton();

    await promise.all([
      tVAUpdatePage.setNameInput('name'),
      tVAUpdatePage.setPercentageValueInput('5'),
      tVAUpdatePage.companySelectLastOption(),
    ]);

    expect(await tVAUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await tVAUpdatePage.getPercentageValueInput()).to.eq('5', 'Expected percentageValue value to be equals to 5');

    await tVAUpdatePage.save();
    expect(await tVAUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tVAComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TVA', async () => {
    const nbButtonsBeforeDelete = await tVAComponentsPage.countDeleteButtons();
    await tVAComponentsPage.clickOnLastDeleteButton();

    tVADeleteDialog = new TVADeleteDialog();
    expect(await tVADeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesTVa.delete.question');
    await tVADeleteDialog.clickOnConfirmButton();

    expect(await tVAComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
