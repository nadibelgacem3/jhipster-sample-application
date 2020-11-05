import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { MovementComponentsPage, MovementDeleteDialog, MovementUpdatePage } from './movement.page-object';

const expect = chai.expect;

describe('Movement e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let movementComponentsPage: MovementComponentsPage;
  let movementUpdatePage: MovementUpdatePage;
  let movementDeleteDialog: MovementDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Movements', async () => {
    await navBarPage.goToEntity('movement');
    movementComponentsPage = new MovementComponentsPage();
    await browser.wait(ec.visibilityOf(movementComponentsPage.title), 5000);
    expect(await movementComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseMovement.home.title');
    await browser.wait(ec.or(ec.visibilityOf(movementComponentsPage.entities), ec.visibilityOf(movementComponentsPage.noResult)), 1000);
  });

  it('should load create Movement page', async () => {
    await movementComponentsPage.clickOnCreateButton();
    movementUpdatePage = new MovementUpdatePage();
    expect(await movementUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseMovement.home.createOrEditLabel');
    await movementUpdatePage.cancel();
  });

  it('should create and save Movements', async () => {
    const nbButtonsBeforeCreate = await movementComponentsPage.countDeleteButtons();

    await movementComponentsPage.clickOnCreateButton();

    await promise.all([
      movementUpdatePage.typeSelectLastOption(),
      movementUpdatePage.reasonSelectLastOption(),
      movementUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      movementUpdatePage.setBillIDInput('5'),
      movementUpdatePage.setTiersIDInput('5'),
      movementUpdatePage.setQuantityInput('5'),
      movementUpdatePage.setCompanyUserIDInput('5'),
      movementUpdatePage.productSelectLastOption(),
    ]);

    expect(await movementUpdatePage.getDateInput()).to.contain('2001-01-01T02:30', 'Expected date value to be equals to 2000-12-31');
    expect(await movementUpdatePage.getBillIDInput()).to.eq('5', 'Expected billID value to be equals to 5');
    expect(await movementUpdatePage.getTiersIDInput()).to.eq('5', 'Expected tiersID value to be equals to 5');
    expect(await movementUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await movementUpdatePage.getCompanyUserIDInput()).to.eq('5', 'Expected companyUserID value to be equals to 5');

    await movementUpdatePage.save();
    expect(await movementUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await movementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Movement', async () => {
    const nbButtonsBeforeDelete = await movementComponentsPage.countDeleteButtons();
    await movementComponentsPage.clickOnLastDeleteButton();

    movementDeleteDialog = new MovementDeleteDialog();
    expect(await movementDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseMovement.delete.question');
    await movementDeleteDialog.clickOnConfirmButton();

    expect(await movementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
