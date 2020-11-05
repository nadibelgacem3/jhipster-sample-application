import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { EmployeeComponentsPage, EmployeeDeleteDialog, EmployeeUpdatePage } from './employee.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Employee e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeComponentsPage: EmployeeComponentsPage;
  let employeeUpdatePage: EmployeeUpdatePage;
  let employeeDeleteDialog: EmployeeDeleteDialog;
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

  it('should load Employees', async () => {
    await navBarPage.goToEntity('employee');
    employeeComponentsPage = new EmployeeComponentsPage();
    await browser.wait(ec.visibilityOf(employeeComponentsPage.title), 5000);
    expect(await employeeComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesEmployee.home.title');
    await browser.wait(ec.or(ec.visibilityOf(employeeComponentsPage.entities), ec.visibilityOf(employeeComponentsPage.noResult)), 1000);
  });

  it('should load create Employee page', async () => {
    await employeeComponentsPage.clickOnCreateButton();
    employeeUpdatePage = new EmployeeUpdatePage();
    expect(await employeeUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincompaniesEmployee.home.createOrEditLabel');
    await employeeUpdatePage.cancel();
  });

  it('should create and save Employees', async () => {
    const nbButtonsBeforeCreate = await employeeComponentsPage.countDeleteButtons();

    await employeeComponentsPage.clickOnCreateButton();

    await promise.all([
      employeeUpdatePage.setFirstNameInput('firstName'),
      employeeUpdatePage.setLastNameInput('lastName'),
      employeeUpdatePage.setJobTitleInput('jobTitle'),
      employeeUpdatePage.genderSelectLastOption(),
      employeeUpdatePage.setDateOfborthInput('2000-12-31'),
      employeeUpdatePage.setDateOfRecruitmentInput('2000-12-31'),
      employeeUpdatePage.setImageInput(absolutePath),
      employeeUpdatePage.setEmailInput('g^[K@,hYGd.C'),
      employeeUpdatePage.setPhoneNumberInput('phoneNumber'),
      employeeUpdatePage.setSalaryInput('5'),
      employeeUpdatePage.setCommissionPctInput('5'),
      employeeUpdatePage.setRatesInput('5'),
      employeeUpdatePage.employeeLocationSelectLastOption(),
      employeeUpdatePage.companySelectLastOption(),
    ]);

    expect(await employeeUpdatePage.getFirstNameInput()).to.eq('firstName', 'Expected FirstName value to be equals to firstName');
    expect(await employeeUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await employeeUpdatePage.getJobTitleInput()).to.eq('jobTitle', 'Expected JobTitle value to be equals to jobTitle');
    expect(await employeeUpdatePage.getDateOfborthInput()).to.eq('2000-12-31', 'Expected dateOfborth value to be equals to 2000-12-31');
    expect(await employeeUpdatePage.getDateOfRecruitmentInput()).to.eq(
      '2000-12-31',
      'Expected dateOfRecruitment value to be equals to 2000-12-31'
    );
    expect(await employeeUpdatePage.getImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected Image value to be end with ' + fileNameToUpload
    );
    expect(await employeeUpdatePage.getEmailInput()).to.eq('g^[K@,hYGd.C', 'Expected Email value to be equals to g^[K@,hYGd.C');
    expect(await employeeUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber', 'Expected PhoneNumber value to be equals to phoneNumber');
    expect(await employeeUpdatePage.getSalaryInput()).to.eq('5', 'Expected salary value to be equals to 5');
    expect(await employeeUpdatePage.getCommissionPctInput()).to.eq('5', 'Expected commissionPct value to be equals to 5');
    expect(await employeeUpdatePage.getRatesInput()).to.eq('5', 'Expected rates value to be equals to 5');

    await employeeUpdatePage.save();
    expect(await employeeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Employee', async () => {
    const nbButtonsBeforeDelete = await employeeComponentsPage.countDeleteButtons();
    await employeeComponentsPage.clickOnLastDeleteButton();

    employeeDeleteDialog = new EmployeeDeleteDialog();
    expect(await employeeDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesEmployee.delete.question');
    await employeeDeleteDialog.clickOnConfirmButton();

    expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
