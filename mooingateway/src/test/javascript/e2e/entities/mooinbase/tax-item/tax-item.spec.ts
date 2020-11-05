import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { TaxItemComponentsPage, TaxItemDeleteDialog, TaxItemUpdatePage } from './tax-item.page-object';

const expect = chai.expect;

describe('TaxItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let taxItemComponentsPage: TaxItemComponentsPage;
  let taxItemUpdatePage: TaxItemUpdatePage;
  let taxItemDeleteDialog: TaxItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TaxItems', async () => {
    await navBarPage.goToEntity('tax-item');
    taxItemComponentsPage = new TaxItemComponentsPage();
    await browser.wait(ec.visibilityOf(taxItemComponentsPage.title), 5000);
    expect(await taxItemComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseTaxItem.home.title');
    await browser.wait(ec.or(ec.visibilityOf(taxItemComponentsPage.entities), ec.visibilityOf(taxItemComponentsPage.noResult)), 1000);
  });

  it('should load create TaxItem page', async () => {
    await taxItemComponentsPage.clickOnCreateButton();
    taxItemUpdatePage = new TaxItemUpdatePage();
    expect(await taxItemUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseTaxItem.home.createOrEditLabel');
    await taxItemUpdatePage.cancel();
  });

  it('should create and save TaxItems', async () => {
    const nbButtonsBeforeCreate = await taxItemComponentsPage.countDeleteButtons();

    await taxItemComponentsPage.clickOnCreateButton();

    await promise.all([
      taxItemUpdatePage.setNameInput('name'),
      taxItemUpdatePage.setValueInput('5'),
      taxItemUpdatePage.setCompanyIDInput('5'),
      taxItemUpdatePage.setTaxIDInput('5'),
      taxItemUpdatePage.productSelectLastOption(),
    ]);

    expect(await taxItemUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    const selectedIsValued = taxItemUpdatePage.getIsValuedInput();
    if (await selectedIsValued.isSelected()) {
      await taxItemUpdatePage.getIsValuedInput().click();
      expect(await taxItemUpdatePage.getIsValuedInput().isSelected(), 'Expected isValued not to be selected').to.be.false;
    } else {
      await taxItemUpdatePage.getIsValuedInput().click();
      expect(await taxItemUpdatePage.getIsValuedInput().isSelected(), 'Expected isValued to be selected').to.be.true;
    }
    const selectedIsPercentage = taxItemUpdatePage.getIsPercentageInput();
    if (await selectedIsPercentage.isSelected()) {
      await taxItemUpdatePage.getIsPercentageInput().click();
      expect(await taxItemUpdatePage.getIsPercentageInput().isSelected(), 'Expected isPercentage not to be selected').to.be.false;
    } else {
      await taxItemUpdatePage.getIsPercentageInput().click();
      expect(await taxItemUpdatePage.getIsPercentageInput().isSelected(), 'Expected isPercentage to be selected').to.be.true;
    }
    expect(await taxItemUpdatePage.getValueInput()).to.eq('5', 'Expected value value to be equals to 5');
    expect(await taxItemUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');
    expect(await taxItemUpdatePage.getTaxIDInput()).to.eq('5', 'Expected taxID value to be equals to 5');

    await taxItemUpdatePage.save();
    expect(await taxItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await taxItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TaxItem', async () => {
    const nbButtonsBeforeDelete = await taxItemComponentsPage.countDeleteButtons();
    await taxItemComponentsPage.clickOnLastDeleteButton();

    taxItemDeleteDialog = new TaxItemDeleteDialog();
    expect(await taxItemDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseTaxItem.delete.question');
    await taxItemDeleteDialog.clickOnConfirmButton();

    expect(await taxItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
