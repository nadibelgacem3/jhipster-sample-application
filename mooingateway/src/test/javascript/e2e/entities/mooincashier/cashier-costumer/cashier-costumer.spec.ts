import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { CashierCostumerComponentsPage, CashierCostumerDeleteDialog, CashierCostumerUpdatePage } from './cashier-costumer.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('CashierCostumer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cashierCostumerComponentsPage: CashierCostumerComponentsPage;
  let cashierCostumerUpdatePage: CashierCostumerUpdatePage;
  let cashierCostumerDeleteDialog: CashierCostumerDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CashierCostumers', async () => {
    await navBarPage.goToEntity('cashier-costumer');
    cashierCostumerComponentsPage = new CashierCostumerComponentsPage();
    await browser.wait(ec.visibilityOf(cashierCostumerComponentsPage.title), 5000);
    expect(await cashierCostumerComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincashierCashierCostumer.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(cashierCostumerComponentsPage.entities), ec.visibilityOf(cashierCostumerComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CashierCostumer page', async () => {
    await cashierCostumerComponentsPage.clickOnCreateButton();
    cashierCostumerUpdatePage = new CashierCostumerUpdatePage();
    expect(await cashierCostumerUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincashierCashierCostumer.home.createOrEditLabel');
    await cashierCostumerUpdatePage.cancel();
  });

  it('should create and save CashierCostumers', async () => {
    const nbButtonsBeforeCreate = await cashierCostumerComponentsPage.countDeleteButtons();

    await cashierCostumerComponentsPage.clickOnCreateButton();

    await promise.all([
      cashierCostumerUpdatePage.setReferenceInput('SC457COM80'),
      cashierCostumerUpdatePage.setFirstNameInput('firstName'),
      cashierCostumerUpdatePage.setLastNameInput('lastName'),
      cashierCostumerUpdatePage.setPhone1Input('phone1'),
      cashierCostumerUpdatePage.setPhone2Input('phone2'),
      cashierCostumerUpdatePage.setImageInput(absolutePath),
      cashierCostumerUpdatePage.setEmailInput('_LAK)@;`&gt;h.z5Mct'),
      cashierCostumerUpdatePage.cashierLocationSelectLastOption(),
      cashierCostumerUpdatePage.cashierSelectLastOption(),
    ]);

    expect(await cashierCostumerUpdatePage.getReferenceInput()).to.eq('SC457COM80', 'Expected Reference value to be equals to SC457COM80');
    expect(await cashierCostumerUpdatePage.getFirstNameInput()).to.eq('firstName', 'Expected FirstName value to be equals to firstName');
    expect(await cashierCostumerUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await cashierCostumerUpdatePage.getPhone1Input()).to.eq('phone1', 'Expected Phone1 value to be equals to phone1');
    expect(await cashierCostumerUpdatePage.getPhone2Input()).to.eq('phone2', 'Expected Phone2 value to be equals to phone2');
    expect(await cashierCostumerUpdatePage.getImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected Image value to be end with ' + fileNameToUpload
    );
    expect(await cashierCostumerUpdatePage.getEmailInput()).to.eq(
      '_LAK)@;`&gt;h.z5Mct',
      'Expected Email value to be equals to _LAK)@;`&gt;h.z5Mct'
    );

    await cashierCostumerUpdatePage.save();
    expect(await cashierCostumerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cashierCostumerComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CashierCostumer', async () => {
    const nbButtonsBeforeDelete = await cashierCostumerComponentsPage.countDeleteButtons();
    await cashierCostumerComponentsPage.clickOnLastDeleteButton();

    cashierCostumerDeleteDialog = new CashierCostumerDeleteDialog();
    expect(await cashierCostumerDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincashierCashierCostumer.delete.question');
    await cashierCostumerDeleteDialog.clickOnConfirmButton();

    expect(await cashierCostumerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
