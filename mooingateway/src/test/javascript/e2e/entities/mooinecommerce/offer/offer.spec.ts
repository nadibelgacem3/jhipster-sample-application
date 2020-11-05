import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { OfferComponentsPage, OfferDeleteDialog, OfferUpdatePage } from './offer.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Offer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let offerComponentsPage: OfferComponentsPage;
  let offerUpdatePage: OfferUpdatePage;
  let offerDeleteDialog: OfferDeleteDialog;
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

  it('should load Offers', async () => {
    await navBarPage.goToEntity('offer');
    offerComponentsPage = new OfferComponentsPage();
    await browser.wait(ec.visibilityOf(offerComponentsPage.title), 5000);
    expect(await offerComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinecommerceOffer.home.title');
    await browser.wait(ec.or(ec.visibilityOf(offerComponentsPage.entities), ec.visibilityOf(offerComponentsPage.noResult)), 1000);
  });

  it('should load create Offer page', async () => {
    await offerComponentsPage.clickOnCreateButton();
    offerUpdatePage = new OfferUpdatePage();
    expect(await offerUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinecommerceOffer.home.createOrEditLabel');
    await offerUpdatePage.cancel();
  });

  it('should create and save Offers', async () => {
    const nbButtonsBeforeCreate = await offerComponentsPage.countDeleteButtons();

    await offerComponentsPage.clickOnCreateButton();

    await promise.all([
      offerUpdatePage.setNameInput('name'),
      offerUpdatePage.setDescriptionInput('description'),
      offerUpdatePage.setImageInput(absolutePath),
      offerUpdatePage.setDateBeginInput('2000-12-31'),
      offerUpdatePage.setDateEndInput('2000-12-31'),
      offerUpdatePage.setDurationInput('PT12S'),
      offerUpdatePage.statusSelectLastOption(),
      offerUpdatePage.setProductIdInput('5'),
      offerUpdatePage.setSaleUnitPriceBeforeDiscountInput('5'),
      offerUpdatePage.setDiscountRateInput('5'),
      offerUpdatePage.setSaleUnitPriceAfterDiscountInput('5'),
      offerUpdatePage.setCompanyIDInput('5'),
    ]);

    expect(await offerUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await offerUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await offerUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);
    expect(await offerUpdatePage.getDateBeginInput()).to.eq('2000-12-31', 'Expected dateBegin value to be equals to 2000-12-31');
    expect(await offerUpdatePage.getDateEndInput()).to.eq('2000-12-31', 'Expected dateEnd value to be equals to 2000-12-31');
    expect(await offerUpdatePage.getDurationInput()).to.contain('12', 'Expected duration value to be equals to 12');
    expect(await offerUpdatePage.getProductIdInput()).to.eq('5', 'Expected productId value to be equals to 5');
    expect(await offerUpdatePage.getSaleUnitPriceBeforeDiscountInput()).to.eq(
      '5',
      'Expected saleUnitPriceBeforeDiscount value to be equals to 5'
    );
    expect(await offerUpdatePage.getDiscountRateInput()).to.eq('5', 'Expected discountRate value to be equals to 5');
    expect(await offerUpdatePage.getSaleUnitPriceAfterDiscountInput()).to.eq(
      '5',
      'Expected saleUnitPriceAfterDiscount value to be equals to 5'
    );
    const selectedWithTVA = offerUpdatePage.getWithTVAInput();
    if (await selectedWithTVA.isSelected()) {
      await offerUpdatePage.getWithTVAInput().click();
      expect(await offerUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA not to be selected').to.be.false;
    } else {
      await offerUpdatePage.getWithTVAInput().click();
      expect(await offerUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA to be selected').to.be.true;
    }
    const selectedWithTax = offerUpdatePage.getWithTaxInput();
    if (await selectedWithTax.isSelected()) {
      await offerUpdatePage.getWithTaxInput().click();
      expect(await offerUpdatePage.getWithTaxInput().isSelected(), 'Expected withTax not to be selected').to.be.false;
    } else {
      await offerUpdatePage.getWithTaxInput().click();
      expect(await offerUpdatePage.getWithTaxInput().isSelected(), 'Expected withTax to be selected').to.be.true;
    }
    const selectedIsActivated = offerUpdatePage.getIsActivatedInput();
    if (await selectedIsActivated.isSelected()) {
      await offerUpdatePage.getIsActivatedInput().click();
      expect(await offerUpdatePage.getIsActivatedInput().isSelected(), 'Expected isActivated not to be selected').to.be.false;
    } else {
      await offerUpdatePage.getIsActivatedInput().click();
      expect(await offerUpdatePage.getIsActivatedInput().isSelected(), 'Expected isActivated to be selected').to.be.true;
    }
    expect(await offerUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');

    await offerUpdatePage.save();
    expect(await offerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await offerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Offer', async () => {
    const nbButtonsBeforeDelete = await offerComponentsPage.countDeleteButtons();
    await offerComponentsPage.clickOnLastDeleteButton();

    offerDeleteDialog = new OfferDeleteDialog();
    expect(await offerDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinecommerceOffer.delete.question');
    await offerDeleteDialog.clickOnConfirmButton();

    expect(await offerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
