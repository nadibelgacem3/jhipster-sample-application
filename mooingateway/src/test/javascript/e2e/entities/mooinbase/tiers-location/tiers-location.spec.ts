import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { TiersLocationComponentsPage, TiersLocationDeleteDialog, TiersLocationUpdatePage } from './tiers-location.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('TiersLocation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tiersLocationComponentsPage: TiersLocationComponentsPage;
  let tiersLocationUpdatePage: TiersLocationUpdatePage;
  let tiersLocationDeleteDialog: TiersLocationDeleteDialog;
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

  it('should load TiersLocations', async () => {
    await navBarPage.goToEntity('tiers-location');
    tiersLocationComponentsPage = new TiersLocationComponentsPage();
    await browser.wait(ec.visibilityOf(tiersLocationComponentsPage.title), 5000);
    expect(await tiersLocationComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseTiersLocation.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(tiersLocationComponentsPage.entities), ec.visibilityOf(tiersLocationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TiersLocation page', async () => {
    await tiersLocationComponentsPage.clickOnCreateButton();
    tiersLocationUpdatePage = new TiersLocationUpdatePage();
    expect(await tiersLocationUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseTiersLocation.home.createOrEditLabel');
    await tiersLocationUpdatePage.cancel();
  });

  it('should create and save TiersLocations', async () => {
    const nbButtonsBeforeCreate = await tiersLocationComponentsPage.countDeleteButtons();

    await tiersLocationComponentsPage.clickOnCreateButton();

    await promise.all([
      tiersLocationUpdatePage.setLocalNumberInput('localNumber'),
      tiersLocationUpdatePage.setStreetAddressInput('streetAddress'),
      tiersLocationUpdatePage.setPostalCodeInput('postalCode'),
      tiersLocationUpdatePage.setCityInput('city'),
      tiersLocationUpdatePage.setStateProvinceInput('stateProvince'),
      tiersLocationUpdatePage.setCountryNameInput('countryName'),
      tiersLocationUpdatePage.setFlagInput(absolutePath),
    ]);

    expect(await tiersLocationUpdatePage.getLocalNumberInput()).to.eq(
      'localNumber',
      'Expected LocalNumber value to be equals to localNumber'
    );
    expect(await tiersLocationUpdatePage.getStreetAddressInput()).to.eq(
      'streetAddress',
      'Expected StreetAddress value to be equals to streetAddress'
    );
    expect(await tiersLocationUpdatePage.getPostalCodeInput()).to.eq('postalCode', 'Expected PostalCode value to be equals to postalCode');
    expect(await tiersLocationUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await tiersLocationUpdatePage.getStateProvinceInput()).to.eq(
      'stateProvince',
      'Expected StateProvince value to be equals to stateProvince'
    );
    expect(await tiersLocationUpdatePage.getCountryNameInput()).to.eq(
      'countryName',
      'Expected CountryName value to be equals to countryName'
    );
    expect(await tiersLocationUpdatePage.getFlagInput()).to.endsWith(
      fileNameToUpload,
      'Expected Flag value to be end with ' + fileNameToUpload
    );

    await tiersLocationUpdatePage.save();
    expect(await tiersLocationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tiersLocationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TiersLocation', async () => {
    const nbButtonsBeforeDelete = await tiersLocationComponentsPage.countDeleteButtons();
    await tiersLocationComponentsPage.clickOnLastDeleteButton();

    tiersLocationDeleteDialog = new TiersLocationDeleteDialog();
    expect(await tiersLocationDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseTiersLocation.delete.question');
    await tiersLocationDeleteDialog.clickOnConfirmButton();

    expect(await tiersLocationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
