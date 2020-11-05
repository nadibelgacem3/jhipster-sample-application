import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  CompanyBillPaymentComponentsPage,
  CompanyBillPaymentDeleteDialog,
  CompanyBillPaymentUpdatePage,
} from './company-bill-payment.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('CompanyBillPayment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyBillPaymentComponentsPage: CompanyBillPaymentComponentsPage;
  let companyBillPaymentUpdatePage: CompanyBillPaymentUpdatePage;
  let companyBillPaymentDeleteDialog: CompanyBillPaymentDeleteDialog;
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

  it('should load CompanyBillPayments', async () => {
    await navBarPage.goToEntity('company-bill-payment');
    companyBillPaymentComponentsPage = new CompanyBillPaymentComponentsPage();
    await browser.wait(ec.visibilityOf(companyBillPaymentComponentsPage.title), 5000);
    expect(await companyBillPaymentComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyBillPayment.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(companyBillPaymentComponentsPage.entities), ec.visibilityOf(companyBillPaymentComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CompanyBillPayment page', async () => {
    await companyBillPaymentComponentsPage.clickOnCreateButton();
    companyBillPaymentUpdatePage = new CompanyBillPaymentUpdatePage();
    expect(await companyBillPaymentUpdatePage.getPageTitle()).to.eq(
      'mooingatewayApp.mooincompaniesCompanyBillPayment.home.createOrEditLabel'
    );
    await companyBillPaymentUpdatePage.cancel();
  });

  it('should create and save CompanyBillPayments', async () => {
    const nbButtonsBeforeCreate = await companyBillPaymentComponentsPage.countDeleteButtons();

    await companyBillPaymentComponentsPage.clickOnCreateButton();

    await promise.all([
      companyBillPaymentUpdatePage.setNumberInput('number'),
      companyBillPaymentUpdatePage.setDetailsInput('details'),
      companyBillPaymentUpdatePage.setPaymentDateInput('2000-12-31'),
      companyBillPaymentUpdatePage.paymentMethodSelectLastOption(),
      companyBillPaymentUpdatePage.setBankCheckorTraitNumberInput('bankCheckorTraitNumber'),
      companyBillPaymentUpdatePage.setImageJustifInput(absolutePath),
      companyBillPaymentUpdatePage.statusSelectLastOption(),
      companyBillPaymentUpdatePage.setTotalHTInput('5'),
      companyBillPaymentUpdatePage.setTotalTVAInput('5'),
      companyBillPaymentUpdatePage.setTotaTTCInput('5'),
      companyBillPaymentUpdatePage.setDateInput('2000-12-31'),
      companyBillPaymentUpdatePage.setDueDateInput('2000-12-31'),
      companyBillPaymentUpdatePage.companySelectLastOption(),
    ]);

    expect(await companyBillPaymentUpdatePage.getNumberInput()).to.eq('number', 'Expected Number value to be equals to number');
    expect(await companyBillPaymentUpdatePage.getDetailsInput()).to.eq('details', 'Expected Details value to be equals to details');
    expect(await companyBillPaymentUpdatePage.getPaymentDateInput()).to.eq(
      '2000-12-31',
      'Expected paymentDate value to be equals to 2000-12-31'
    );
    expect(await companyBillPaymentUpdatePage.getBankCheckorTraitNumberInput()).to.eq(
      'bankCheckorTraitNumber',
      'Expected BankCheckorTraitNumber value to be equals to bankCheckorTraitNumber'
    );
    expect(await companyBillPaymentUpdatePage.getImageJustifInput()).to.endsWith(
      fileNameToUpload,
      'Expected ImageJustif value to be end with ' + fileNameToUpload
    );
    expect(await companyBillPaymentUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await companyBillPaymentUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await companyBillPaymentUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');
    expect(await companyBillPaymentUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');
    expect(await companyBillPaymentUpdatePage.getDueDateInput()).to.eq('2000-12-31', 'Expected dueDate value to be equals to 2000-12-31');
    const selectedWithTVA = companyBillPaymentUpdatePage.getWithTVAInput();
    if (await selectedWithTVA.isSelected()) {
      await companyBillPaymentUpdatePage.getWithTVAInput().click();
      expect(await companyBillPaymentUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA not to be selected').to.be.false;
    } else {
      await companyBillPaymentUpdatePage.getWithTVAInput().click();
      expect(await companyBillPaymentUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA to be selected').to.be.true;
    }
    const selectedWithTax = companyBillPaymentUpdatePage.getWithTaxInput();
    if (await selectedWithTax.isSelected()) {
      await companyBillPaymentUpdatePage.getWithTaxInput().click();
      expect(await companyBillPaymentUpdatePage.getWithTaxInput().isSelected(), 'Expected withTax not to be selected').to.be.false;
    } else {
      await companyBillPaymentUpdatePage.getWithTaxInput().click();
      expect(await companyBillPaymentUpdatePage.getWithTaxInput().isSelected(), 'Expected withTax to be selected').to.be.true;
    }

    await companyBillPaymentUpdatePage.save();
    expect(await companyBillPaymentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await companyBillPaymentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CompanyBillPayment', async () => {
    const nbButtonsBeforeDelete = await companyBillPaymentComponentsPage.countDeleteButtons();
    await companyBillPaymentComponentsPage.clickOnLastDeleteButton();

    companyBillPaymentDeleteDialog = new CompanyBillPaymentDeleteDialog();
    expect(await companyBillPaymentDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyBillPayment.delete.question');
    await companyBillPaymentDeleteDialog.clickOnConfirmButton();

    expect(await companyBillPaymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
