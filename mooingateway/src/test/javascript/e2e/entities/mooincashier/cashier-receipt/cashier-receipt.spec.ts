import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { CashierReceiptComponentsPage, CashierReceiptDeleteDialog, CashierReceiptUpdatePage } from './cashier-receipt.page-object';

const expect = chai.expect;

describe('CashierReceipt e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cashierReceiptComponentsPage: CashierReceiptComponentsPage;
  let cashierReceiptUpdatePage: CashierReceiptUpdatePage;
  let cashierReceiptDeleteDialog: CashierReceiptDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CashierReceipts', async () => {
    await navBarPage.goToEntity('cashier-receipt');
    cashierReceiptComponentsPage = new CashierReceiptComponentsPage();
    await browser.wait(ec.visibilityOf(cashierReceiptComponentsPage.title), 5000);
    expect(await cashierReceiptComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincashierCashierReceipt.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(cashierReceiptComponentsPage.entities), ec.visibilityOf(cashierReceiptComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CashierReceipt page', async () => {
    await cashierReceiptComponentsPage.clickOnCreateButton();
    cashierReceiptUpdatePage = new CashierReceiptUpdatePage();
    expect(await cashierReceiptUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincashierCashierReceipt.home.createOrEditLabel');
    await cashierReceiptUpdatePage.cancel();
  });

  it('should create and save CashierReceipts', async () => {
    const nbButtonsBeforeCreate = await cashierReceiptComponentsPage.countDeleteButtons();

    await cashierReceiptComponentsPage.clickOnCreateButton();

    await promise.all([
      cashierReceiptUpdatePage.setNumberInput('number'),
      cashierReceiptUpdatePage.setReferenceInput('reference'),
      cashierReceiptUpdatePage.statusSelectLastOption(),
      cashierReceiptUpdatePage.setTotalHTInput('5'),
      cashierReceiptUpdatePage.setTotalTVAInput('5'),
      cashierReceiptUpdatePage.setTotaTTCInput('5'),
      cashierReceiptUpdatePage.setDateInput('2000-12-31'),
      cashierReceiptUpdatePage.setDueDateInput('2000-12-31'),
      cashierReceiptUpdatePage.cashierSelectLastOption(),
      cashierReceiptUpdatePage.cashierCostumerSelectLastOption(),
    ]);

    expect(await cashierReceiptUpdatePage.getNumberInput()).to.eq('number', 'Expected Number value to be equals to number');
    expect(await cashierReceiptUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');
    expect(await cashierReceiptUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await cashierReceiptUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await cashierReceiptUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');
    expect(await cashierReceiptUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');
    expect(await cashierReceiptUpdatePage.getDueDateInput()).to.eq('2000-12-31', 'Expected dueDate value to be equals to 2000-12-31');
    const selectedIsConverted = cashierReceiptUpdatePage.getIsConvertedInput();
    if (await selectedIsConverted.isSelected()) {
      await cashierReceiptUpdatePage.getIsConvertedInput().click();
      expect(await cashierReceiptUpdatePage.getIsConvertedInput().isSelected(), 'Expected isConverted not to be selected').to.be.false;
    } else {
      await cashierReceiptUpdatePage.getIsConvertedInput().click();
      expect(await cashierReceiptUpdatePage.getIsConvertedInput().isSelected(), 'Expected isConverted to be selected').to.be.true;
    }
    const selectedWithTVA = cashierReceiptUpdatePage.getWithTVAInput();
    if (await selectedWithTVA.isSelected()) {
      await cashierReceiptUpdatePage.getWithTVAInput().click();
      expect(await cashierReceiptUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA not to be selected').to.be.false;
    } else {
      await cashierReceiptUpdatePage.getWithTVAInput().click();
      expect(await cashierReceiptUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA to be selected').to.be.true;
    }
    const selectedWithTax = cashierReceiptUpdatePage.getWithTaxInput();
    if (await selectedWithTax.isSelected()) {
      await cashierReceiptUpdatePage.getWithTaxInput().click();
      expect(await cashierReceiptUpdatePage.getWithTaxInput().isSelected(), 'Expected withTax not to be selected').to.be.false;
    } else {
      await cashierReceiptUpdatePage.getWithTaxInput().click();
      expect(await cashierReceiptUpdatePage.getWithTaxInput().isSelected(), 'Expected withTax to be selected').to.be.true;
    }

    await cashierReceiptUpdatePage.save();
    expect(await cashierReceiptUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cashierReceiptComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CashierReceipt', async () => {
    const nbButtonsBeforeDelete = await cashierReceiptComponentsPage.countDeleteButtons();
    await cashierReceiptComponentsPage.clickOnLastDeleteButton();

    cashierReceiptDeleteDialog = new CashierReceiptDeleteDialog();
    expect(await cashierReceiptDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincashierCashierReceipt.delete.question');
    await cashierReceiptDeleteDialog.clickOnConfirmButton();

    expect(await cashierReceiptComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
