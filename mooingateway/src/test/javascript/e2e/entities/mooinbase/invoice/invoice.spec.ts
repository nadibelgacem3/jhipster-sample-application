import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { InvoiceComponentsPage, InvoiceDeleteDialog, InvoiceUpdatePage } from './invoice.page-object';

const expect = chai.expect;

describe('Invoice e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let invoiceComponentsPage: InvoiceComponentsPage;
  let invoiceUpdatePage: InvoiceUpdatePage;
  let invoiceDeleteDialog: InvoiceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Invoices', async () => {
    await navBarPage.goToEntity('invoice');
    invoiceComponentsPage = new InvoiceComponentsPage();
    await browser.wait(ec.visibilityOf(invoiceComponentsPage.title), 5000);
    expect(await invoiceComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseInvoice.home.title');
    await browser.wait(ec.or(ec.visibilityOf(invoiceComponentsPage.entities), ec.visibilityOf(invoiceComponentsPage.noResult)), 1000);
  });

  it('should load create Invoice page', async () => {
    await invoiceComponentsPage.clickOnCreateButton();
    invoiceUpdatePage = new InvoiceUpdatePage();
    expect(await invoiceUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseInvoice.home.createOrEditLabel');
    await invoiceUpdatePage.cancel();
  });

  it('should create and save Invoices', async () => {
    const nbButtonsBeforeCreate = await invoiceComponentsPage.countDeleteButtons();

    await invoiceComponentsPage.clickOnCreateButton();

    await promise.all([
      invoiceUpdatePage.setNumberInput('number'),
      invoiceUpdatePage.setReferenceInput('reference'),
      invoiceUpdatePage.statusSelectLastOption(),
      invoiceUpdatePage.typeSelectLastOption(),
      invoiceUpdatePage.setTotalHTInput('5'),
      invoiceUpdatePage.setTotalTVAInput('5'),
      invoiceUpdatePage.setTotaTTCInput('5'),
      invoiceUpdatePage.setDateInput('2000-12-31'),
      invoiceUpdatePage.setDueDateInput('2000-12-31'),
      invoiceUpdatePage.setCompanyIDInput('5'),
      invoiceUpdatePage.tiersSelectLastOption(),
    ]);

    expect(await invoiceUpdatePage.getNumberInput()).to.eq('number', 'Expected Number value to be equals to number');
    expect(await invoiceUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');
    expect(await invoiceUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await invoiceUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await invoiceUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');
    expect(await invoiceUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');
    expect(await invoiceUpdatePage.getDueDateInput()).to.eq('2000-12-31', 'Expected dueDate value to be equals to 2000-12-31');
    expect(await invoiceUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');

    await invoiceUpdatePage.save();
    expect(await invoiceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await invoiceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Invoice', async () => {
    const nbButtonsBeforeDelete = await invoiceComponentsPage.countDeleteButtons();
    await invoiceComponentsPage.clickOnLastDeleteButton();

    invoiceDeleteDialog = new InvoiceDeleteDialog();
    expect(await invoiceDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseInvoice.delete.question');
    await invoiceDeleteDialog.clickOnConfirmButton();

    expect(await invoiceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
