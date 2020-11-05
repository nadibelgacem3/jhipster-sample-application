import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { EmployeeLocationComponentsPage, EmployeeLocationDeleteDialog, EmployeeLocationUpdatePage } from './employee-location.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('EmployeeLocation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeLocationComponentsPage: EmployeeLocationComponentsPage;
  let employeeLocationUpdatePage: EmployeeLocationUpdatePage;
  let employeeLocationDeleteDialog: EmployeeLocationDeleteDialog;
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

  it('should load EmployeeLocations', async () => {
    await navBarPage.goToEntity('employee-location');
    employeeLocationComponentsPage = new EmployeeLocationComponentsPage();
    await browser.wait(ec.visibilityOf(employeeLocationComponentsPage.title), 5000);
    expect(await employeeLocationComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesEmployeeLocation.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(employeeLocationComponentsPage.entities), ec.visibilityOf(employeeLocationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmployeeLocation page', async () => {
    await employeeLocationComponentsPage.clickOnCreateButton();
    employeeLocationUpdatePage = new EmployeeLocationUpdatePage();
    expect(await employeeLocationUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincompaniesEmployeeLocation.home.createOrEditLabel');
    await employeeLocationUpdatePage.cancel();
  });

  it('should create and save EmployeeLocations', async () => {
    const nbButtonsBeforeCreate = await employeeLocationComponentsPage.countDeleteButtons();

    await employeeLocationComponentsPage.clickOnCreateButton();

    await promise.all([
      employeeLocationUpdatePage.setLocalNumberInput('localNumber'),
      employeeLocationUpdatePage.setStreetAddressInput('streetAddress'),
      employeeLocationUpdatePage.setPostalCodeInput('postalCode'),
      employeeLocationUpdatePage.setCityInput('city'),
      employeeLocationUpdatePage.setStateProvinceInput('stateProvince'),
      employeeLocationUpdatePage.setCountryNameInput('countryName'),
      employeeLocationUpdatePage.setFlagInput(absolutePath),
    ]);

    expect(await employeeLocationUpdatePage.getLocalNumberInput()).to.eq(
      'localNumber',
      'Expected LocalNumber value to be equals to localNumber'
    );
    expect(await employeeLocationUpdatePage.getStreetAddressInput()).to.eq(
      'streetAddress',
      'Expected StreetAddress value to be equals to streetAddress'
    );
    expect(await employeeLocationUpdatePage.getPostalCodeInput()).to.eq(
      'postalCode',
      'Expected PostalCode value to be equals to postalCode'
    );
    expect(await employeeLocationUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await employeeLocationUpdatePage.getStateProvinceInput()).to.eq(
      'stateProvince',
      'Expected StateProvince value to be equals to stateProvince'
    );
    expect(await employeeLocationUpdatePage.getCountryNameInput()).to.eq(
      'countryName',
      'Expected CountryName value to be equals to countryName'
    );
    expect(await employeeLocationUpdatePage.getFlagInput()).to.endsWith(
      fileNameToUpload,
      'Expected Flag value to be end with ' + fileNameToUpload
    );

    await employeeLocationUpdatePage.save();
    expect(await employeeLocationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeeLocationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmployeeLocation', async () => {
    const nbButtonsBeforeDelete = await employeeLocationComponentsPage.countDeleteButtons();
    await employeeLocationComponentsPage.clickOnLastDeleteButton();

    employeeLocationDeleteDialog = new EmployeeLocationDeleteDialog();
    expect(await employeeLocationDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesEmployeeLocation.delete.question');
    await employeeLocationDeleteDialog.clickOnConfirmButton();

    expect(await employeeLocationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
