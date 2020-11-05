import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { CompanyLocationComponentsPage, CompanyLocationDeleteDialog, CompanyLocationUpdatePage } from './company-location.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('CompanyLocation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyLocationComponentsPage: CompanyLocationComponentsPage;
  let companyLocationUpdatePage: CompanyLocationUpdatePage;
  let companyLocationDeleteDialog: CompanyLocationDeleteDialog;
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

  it('should load CompanyLocations', async () => {
    await navBarPage.goToEntity('company-location');
    companyLocationComponentsPage = new CompanyLocationComponentsPage();
    await browser.wait(ec.visibilityOf(companyLocationComponentsPage.title), 5000);
    expect(await companyLocationComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyLocation.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(companyLocationComponentsPage.entities), ec.visibilityOf(companyLocationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CompanyLocation page', async () => {
    await companyLocationComponentsPage.clickOnCreateButton();
    companyLocationUpdatePage = new CompanyLocationUpdatePage();
    expect(await companyLocationUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyLocation.home.createOrEditLabel');
    await companyLocationUpdatePage.cancel();
  });

  it('should create and save CompanyLocations', async () => {
    const nbButtonsBeforeCreate = await companyLocationComponentsPage.countDeleteButtons();

    await companyLocationComponentsPage.clickOnCreateButton();

    await promise.all([
      companyLocationUpdatePage.setLocalNumberInput('localNumber'),
      companyLocationUpdatePage.setStreetAddressInput('streetAddress'),
      companyLocationUpdatePage.setPostalCodeInput('postalCode'),
      companyLocationUpdatePage.setCityInput('city'),
      companyLocationUpdatePage.setStateProvinceInput('stateProvince'),
      companyLocationUpdatePage.setCountryNameInput('countryName'),
      companyLocationUpdatePage.setFlagInput(absolutePath),
    ]);

    expect(await companyLocationUpdatePage.getLocalNumberInput()).to.eq(
      'localNumber',
      'Expected LocalNumber value to be equals to localNumber'
    );
    expect(await companyLocationUpdatePage.getStreetAddressInput()).to.eq(
      'streetAddress',
      'Expected StreetAddress value to be equals to streetAddress'
    );
    expect(await companyLocationUpdatePage.getPostalCodeInput()).to.eq(
      'postalCode',
      'Expected PostalCode value to be equals to postalCode'
    );
    expect(await companyLocationUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await companyLocationUpdatePage.getStateProvinceInput()).to.eq(
      'stateProvince',
      'Expected StateProvince value to be equals to stateProvince'
    );
    expect(await companyLocationUpdatePage.getCountryNameInput()).to.eq(
      'countryName',
      'Expected CountryName value to be equals to countryName'
    );
    expect(await companyLocationUpdatePage.getFlagInput()).to.endsWith(
      fileNameToUpload,
      'Expected Flag value to be end with ' + fileNameToUpload
    );

    await companyLocationUpdatePage.save();
    expect(await companyLocationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await companyLocationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CompanyLocation', async () => {
    const nbButtonsBeforeDelete = await companyLocationComponentsPage.countDeleteButtons();
    await companyLocationComponentsPage.clickOnLastDeleteButton();

    companyLocationDeleteDialog = new CompanyLocationDeleteDialog();
    expect(await companyLocationDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyLocation.delete.question');
    await companyLocationDeleteDialog.clickOnConfirmButton();

    expect(await companyLocationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
