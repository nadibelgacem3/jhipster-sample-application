import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { TiersComponentsPage, TiersDeleteDialog, TiersUpdatePage } from './tiers.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Tiers e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tiersComponentsPage: TiersComponentsPage;
  let tiersUpdatePage: TiersUpdatePage;
  let tiersDeleteDialog: TiersDeleteDialog;
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

  it('should load Tiers', async () => {
    await navBarPage.goToEntity('tiers');
    tiersComponentsPage = new TiersComponentsPage();
    await browser.wait(ec.visibilityOf(tiersComponentsPage.title), 5000);
    expect(await tiersComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseTiers.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tiersComponentsPage.entities), ec.visibilityOf(tiersComponentsPage.noResult)), 1000);
  });

  it('should load create Tiers page', async () => {
    await tiersComponentsPage.clickOnCreateButton();
    tiersUpdatePage = new TiersUpdatePage();
    expect(await tiersUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseTiers.home.createOrEditLabel');
    await tiersUpdatePage.cancel();
  });

  it('should create and save Tiers', async () => {
    const nbButtonsBeforeCreate = await tiersComponentsPage.countDeleteButtons();

    await tiersComponentsPage.clickOnCreateButton();

    await promise.all([
      tiersUpdatePage.setReferenceInput('reference'),
      tiersUpdatePage.setFirstNameInput('firstName'),
      tiersUpdatePage.setLastNameInput('lastName'),
      tiersUpdatePage.setPhone1Input('phone1'),
      tiersUpdatePage.setPhone2Input('phone2'),
      tiersUpdatePage.setImageInput(absolutePath),
      tiersUpdatePage.setEmailInput('u@YYSy{y.Z'),
      tiersUpdatePage.typeSelectLastOption(),
      tiersUpdatePage.setCompanyIDInput('5'),
      tiersUpdatePage.tiersLocationSelectLastOption(),
    ]);

    expect(await tiersUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');
    expect(await tiersUpdatePage.getFirstNameInput()).to.eq('firstName', 'Expected FirstName value to be equals to firstName');
    expect(await tiersUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await tiersUpdatePage.getPhone1Input()).to.eq('phone1', 'Expected Phone1 value to be equals to phone1');
    expect(await tiersUpdatePage.getPhone2Input()).to.eq('phone2', 'Expected Phone2 value to be equals to phone2');
    expect(await tiersUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);
    expect(await tiersUpdatePage.getEmailInput()).to.eq('u@YYSy{y.Z', 'Expected Email value to be equals to u@YYSy{y.Z');
    const selectedIsCustomer = tiersUpdatePage.getIsCustomerInput();
    if (await selectedIsCustomer.isSelected()) {
      await tiersUpdatePage.getIsCustomerInput().click();
      expect(await tiersUpdatePage.getIsCustomerInput().isSelected(), 'Expected isCustomer not to be selected').to.be.false;
    } else {
      await tiersUpdatePage.getIsCustomerInput().click();
      expect(await tiersUpdatePage.getIsCustomerInput().isSelected(), 'Expected isCustomer to be selected').to.be.true;
    }
    const selectedIsSupplier = tiersUpdatePage.getIsSupplierInput();
    if (await selectedIsSupplier.isSelected()) {
      await tiersUpdatePage.getIsSupplierInput().click();
      expect(await tiersUpdatePage.getIsSupplierInput().isSelected(), 'Expected isSupplier not to be selected').to.be.false;
    } else {
      await tiersUpdatePage.getIsSupplierInput().click();
      expect(await tiersUpdatePage.getIsSupplierInput().isSelected(), 'Expected isSupplier to be selected').to.be.true;
    }
    expect(await tiersUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');

    await tiersUpdatePage.save();
    expect(await tiersUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tiersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Tiers', async () => {
    const nbButtonsBeforeDelete = await tiersComponentsPage.countDeleteButtons();
    await tiersComponentsPage.clickOnLastDeleteButton();

    tiersDeleteDialog = new TiersDeleteDialog();
    expect(await tiersDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseTiers.delete.question');
    await tiersDeleteDialog.clickOnConfirmButton();

    expect(await tiersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
