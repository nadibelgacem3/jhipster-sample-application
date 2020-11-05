import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { CashierLocationComponentsPage, CashierLocationDeleteDialog, CashierLocationUpdatePage } from './cashier-location.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('CashierLocation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cashierLocationComponentsPage: CashierLocationComponentsPage;
  let cashierLocationUpdatePage: CashierLocationUpdatePage;
  let cashierLocationDeleteDialog: CashierLocationDeleteDialog;
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

  it('should load CashierLocations', async () => {
    await navBarPage.goToEntity('cashier-location');
    cashierLocationComponentsPage = new CashierLocationComponentsPage();
    await browser.wait(ec.visibilityOf(cashierLocationComponentsPage.title), 5000);
    expect(await cashierLocationComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincashierCashierLocation.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(cashierLocationComponentsPage.entities), ec.visibilityOf(cashierLocationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CashierLocation page', async () => {
    await cashierLocationComponentsPage.clickOnCreateButton();
    cashierLocationUpdatePage = new CashierLocationUpdatePage();
    expect(await cashierLocationUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincashierCashierLocation.home.createOrEditLabel');
    await cashierLocationUpdatePage.cancel();
  });

  it('should create and save CashierLocations', async () => {
    const nbButtonsBeforeCreate = await cashierLocationComponentsPage.countDeleteButtons();

    await cashierLocationComponentsPage.clickOnCreateButton();

    await promise.all([
      cashierLocationUpdatePage.setLocalNumberInput('localNumber'),
      cashierLocationUpdatePage.setStreetAddressInput('streetAddress'),
      cashierLocationUpdatePage.setPostalCodeInput('postalCode'),
      cashierLocationUpdatePage.setCityInput('city'),
      cashierLocationUpdatePage.setStateProvinceInput('stateProvince'),
      cashierLocationUpdatePage.setCountryNameInput('countryName'),
      cashierLocationUpdatePage.setFlagInput(absolutePath),
    ]);

    expect(await cashierLocationUpdatePage.getLocalNumberInput()).to.eq(
      'localNumber',
      'Expected LocalNumber value to be equals to localNumber'
    );
    expect(await cashierLocationUpdatePage.getStreetAddressInput()).to.eq(
      'streetAddress',
      'Expected StreetAddress value to be equals to streetAddress'
    );
    expect(await cashierLocationUpdatePage.getPostalCodeInput()).to.eq(
      'postalCode',
      'Expected PostalCode value to be equals to postalCode'
    );
    expect(await cashierLocationUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await cashierLocationUpdatePage.getStateProvinceInput()).to.eq(
      'stateProvince',
      'Expected StateProvince value to be equals to stateProvince'
    );
    expect(await cashierLocationUpdatePage.getCountryNameInput()).to.eq(
      'countryName',
      'Expected CountryName value to be equals to countryName'
    );
    expect(await cashierLocationUpdatePage.getFlagInput()).to.endsWith(
      fileNameToUpload,
      'Expected Flag value to be end with ' + fileNameToUpload
    );

    await cashierLocationUpdatePage.save();
    expect(await cashierLocationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cashierLocationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CashierLocation', async () => {
    const nbButtonsBeforeDelete = await cashierLocationComponentsPage.countDeleteButtons();
    await cashierLocationComponentsPage.clickOnLastDeleteButton();

    cashierLocationDeleteDialog = new CashierLocationDeleteDialog();
    expect(await cashierLocationDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincashierCashierLocation.delete.question');
    await cashierLocationDeleteDialog.clickOnConfirmButton();

    expect(await cashierLocationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
