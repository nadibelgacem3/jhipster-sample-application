import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  CashierReceiptPayComponentsPage,
  CashierReceiptPayDeleteDialog,
  CashierReceiptPayUpdatePage,
} from './cashier-receipt-pay.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('CashierReceiptPay e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cashierReceiptPayComponentsPage: CashierReceiptPayComponentsPage;
  let cashierReceiptPayUpdatePage: CashierReceiptPayUpdatePage;
  let cashierReceiptPayDeleteDialog: CashierReceiptPayDeleteDialog;
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

  it('should load CashierReceiptPays', async () => {
    await navBarPage.goToEntity('cashier-receipt-pay');
    cashierReceiptPayComponentsPage = new CashierReceiptPayComponentsPage();
    await browser.wait(ec.visibilityOf(cashierReceiptPayComponentsPage.title), 5000);
    expect(await cashierReceiptPayComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincashierCashierReceiptPay.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(cashierReceiptPayComponentsPage.entities), ec.visibilityOf(cashierReceiptPayComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CashierReceiptPay page', async () => {
    await cashierReceiptPayComponentsPage.clickOnCreateButton();
    cashierReceiptPayUpdatePage = new CashierReceiptPayUpdatePage();
    expect(await cashierReceiptPayUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincashierCashierReceiptPay.home.createOrEditLabel');
    await cashierReceiptPayUpdatePage.cancel();
  });

  it('should create and save CashierReceiptPays', async () => {
    const nbButtonsBeforeCreate = await cashierReceiptPayComponentsPage.countDeleteButtons();

    await cashierReceiptPayComponentsPage.clickOnCreateButton();

    await promise.all([
      cashierReceiptPayUpdatePage.setDetailsInput('details'),
      cashierReceiptPayUpdatePage.setAmountInput('5'),
      cashierReceiptPayUpdatePage.setPaymentDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      cashierReceiptPayUpdatePage.paymentMethodSelectLastOption(),
      cashierReceiptPayUpdatePage.setBankCheckorTraitNumberInput('bankCheckorTraitNumber'),
      cashierReceiptPayUpdatePage.setImageJustifInput(absolutePath),
      cashierReceiptPayUpdatePage.cashierReceiptSelectLastOption(),
    ]);

    expect(await cashierReceiptPayUpdatePage.getDetailsInput()).to.eq('details', 'Expected Details value to be equals to details');
    expect(await cashierReceiptPayUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await cashierReceiptPayUpdatePage.getPaymentDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected paymentDate value to be equals to 2000-12-31'
    );
    expect(await cashierReceiptPayUpdatePage.getBankCheckorTraitNumberInput()).to.eq(
      'bankCheckorTraitNumber',
      'Expected BankCheckorTraitNumber value to be equals to bankCheckorTraitNumber'
    );
    expect(await cashierReceiptPayUpdatePage.getImageJustifInput()).to.endsWith(
      fileNameToUpload,
      'Expected ImageJustif value to be end with ' + fileNameToUpload
    );

    await cashierReceiptPayUpdatePage.save();
    expect(await cashierReceiptPayUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cashierReceiptPayComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CashierReceiptPay', async () => {
    const nbButtonsBeforeDelete = await cashierReceiptPayComponentsPage.countDeleteButtons();
    await cashierReceiptPayComponentsPage.clickOnLastDeleteButton();

    cashierReceiptPayDeleteDialog = new CashierReceiptPayDeleteDialog();
    expect(await cashierReceiptPayDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincashierCashierReceiptPay.delete.question');
    await cashierReceiptPayDeleteDialog.clickOnConfirmButton();

    expect(await cashierReceiptPayComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
