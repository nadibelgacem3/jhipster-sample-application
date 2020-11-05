import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  InvoicePaymentHistoryComponentsPage,
  InvoicePaymentHistoryDeleteDialog,
  InvoicePaymentHistoryUpdatePage,
} from './invoice-payment-history.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('InvoicePaymentHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let invoicePaymentHistoryComponentsPage: InvoicePaymentHistoryComponentsPage;
  let invoicePaymentHistoryUpdatePage: InvoicePaymentHistoryUpdatePage;
  let invoicePaymentHistoryDeleteDialog: InvoicePaymentHistoryDeleteDialog;
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

  it('should load InvoicePaymentHistories', async () => {
    await navBarPage.goToEntity('invoice-payment-history');
    invoicePaymentHistoryComponentsPage = new InvoicePaymentHistoryComponentsPage();
    await browser.wait(ec.visibilityOf(invoicePaymentHistoryComponentsPage.title), 5000);
    expect(await invoicePaymentHistoryComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseInvoicePaymentHistory.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(invoicePaymentHistoryComponentsPage.entities), ec.visibilityOf(invoicePaymentHistoryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InvoicePaymentHistory page', async () => {
    await invoicePaymentHistoryComponentsPage.clickOnCreateButton();
    invoicePaymentHistoryUpdatePage = new InvoicePaymentHistoryUpdatePage();
    expect(await invoicePaymentHistoryUpdatePage.getPageTitle()).to.eq(
      'mooingatewayApp.mooinbaseInvoicePaymentHistory.home.createOrEditLabel'
    );
    await invoicePaymentHistoryUpdatePage.cancel();
  });

  it('should create and save InvoicePaymentHistories', async () => {
    const nbButtonsBeforeCreate = await invoicePaymentHistoryComponentsPage.countDeleteButtons();

    await invoicePaymentHistoryComponentsPage.clickOnCreateButton();

    await promise.all([
      invoicePaymentHistoryUpdatePage.setDetailsInput('details'),
      invoicePaymentHistoryUpdatePage.setAmountInput('5'),
      invoicePaymentHistoryUpdatePage.setPaymentDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      invoicePaymentHistoryUpdatePage.paymentMethodSelectLastOption(),
      invoicePaymentHistoryUpdatePage.setBankCheckorTraitNumberInput('bankCheckorTraitNumber'),
      invoicePaymentHistoryUpdatePage.setImageJustifInput(absolutePath),
      invoicePaymentHistoryUpdatePage.invoiceSelectLastOption(),
    ]);

    expect(await invoicePaymentHistoryUpdatePage.getDetailsInput()).to.eq('details', 'Expected Details value to be equals to details');
    expect(await invoicePaymentHistoryUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await invoicePaymentHistoryUpdatePage.getPaymentDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected paymentDate value to be equals to 2000-12-31'
    );
    expect(await invoicePaymentHistoryUpdatePage.getBankCheckorTraitNumberInput()).to.eq(
      'bankCheckorTraitNumber',
      'Expected BankCheckorTraitNumber value to be equals to bankCheckorTraitNumber'
    );
    expect(await invoicePaymentHistoryUpdatePage.getImageJustifInput()).to.endsWith(
      fileNameToUpload,
      'Expected ImageJustif value to be end with ' + fileNameToUpload
    );

    await invoicePaymentHistoryUpdatePage.save();
    expect(await invoicePaymentHistoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await invoicePaymentHistoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last InvoicePaymentHistory', async () => {
    const nbButtonsBeforeDelete = await invoicePaymentHistoryComponentsPage.countDeleteButtons();
    await invoicePaymentHistoryComponentsPage.clickOnLastDeleteButton();

    invoicePaymentHistoryDeleteDialog = new InvoicePaymentHistoryDeleteDialog();
    expect(await invoicePaymentHistoryDeleteDialog.getDialogTitle()).to.eq(
      'mooingatewayApp.mooinbaseInvoicePaymentHistory.delete.question'
    );
    await invoicePaymentHistoryDeleteDialog.clickOnConfirmButton();

    expect(await invoicePaymentHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
