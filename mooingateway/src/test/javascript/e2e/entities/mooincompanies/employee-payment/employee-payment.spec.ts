import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { EmployeePaymentComponentsPage, EmployeePaymentDeleteDialog, EmployeePaymentUpdatePage } from './employee-payment.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('EmployeePayment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeePaymentComponentsPage: EmployeePaymentComponentsPage;
  let employeePaymentUpdatePage: EmployeePaymentUpdatePage;
  let employeePaymentDeleteDialog: EmployeePaymentDeleteDialog;
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

  it('should load EmployeePayments', async () => {
    await navBarPage.goToEntity('employee-payment');
    employeePaymentComponentsPage = new EmployeePaymentComponentsPage();
    await browser.wait(ec.visibilityOf(employeePaymentComponentsPage.title), 5000);
    expect(await employeePaymentComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesEmployeePayment.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(employeePaymentComponentsPage.entities), ec.visibilityOf(employeePaymentComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmployeePayment page', async () => {
    await employeePaymentComponentsPage.clickOnCreateButton();
    employeePaymentUpdatePage = new EmployeePaymentUpdatePage();
    expect(await employeePaymentUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincompaniesEmployeePayment.home.createOrEditLabel');
    await employeePaymentUpdatePage.cancel();
  });

  it('should create and save EmployeePayments', async () => {
    const nbButtonsBeforeCreate = await employeePaymentComponentsPage.countDeleteButtons();

    await employeePaymentComponentsPage.clickOnCreateButton();

    await promise.all([
      employeePaymentUpdatePage.setDetailsInput('details'),
      employeePaymentUpdatePage.setAmountInput('5'),
      employeePaymentUpdatePage.setPaymentDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeePaymentUpdatePage.setFromDateInput('2000-12-31'),
      employeePaymentUpdatePage.setToDateInput('2000-12-31'),
      employeePaymentUpdatePage.setImageJustifInput(absolutePath),
      employeePaymentUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await employeePaymentUpdatePage.getDetailsInput()).to.eq('details', 'Expected Details value to be equals to details');
    expect(await employeePaymentUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await employeePaymentUpdatePage.getPaymentDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected paymentDate value to be equals to 2000-12-31'
    );
    expect(await employeePaymentUpdatePage.getFromDateInput()).to.eq('2000-12-31', 'Expected fromDate value to be equals to 2000-12-31');
    expect(await employeePaymentUpdatePage.getToDateInput()).to.eq('2000-12-31', 'Expected toDate value to be equals to 2000-12-31');
    expect(await employeePaymentUpdatePage.getImageJustifInput()).to.endsWith(
      fileNameToUpload,
      'Expected ImageJustif value to be end with ' + fileNameToUpload
    );

    await employeePaymentUpdatePage.save();
    expect(await employeePaymentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeePaymentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmployeePayment', async () => {
    const nbButtonsBeforeDelete = await employeePaymentComponentsPage.countDeleteButtons();
    await employeePaymentComponentsPage.clickOnLastDeleteButton();

    employeePaymentDeleteDialog = new EmployeePaymentDeleteDialog();
    expect(await employeePaymentDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesEmployeePayment.delete.question');
    await employeePaymentDeleteDialog.clickOnConfirmButton();

    expect(await employeePaymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
