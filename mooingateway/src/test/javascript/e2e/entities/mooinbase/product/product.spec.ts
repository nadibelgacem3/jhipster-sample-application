import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ProductComponentsPage, ProductDeleteDialog, ProductUpdatePage } from './product.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Product e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productComponentsPage: ProductComponentsPage;
  let productUpdatePage: ProductUpdatePage;
  let productDeleteDialog: ProductDeleteDialog;
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

  it('should load Products', async () => {
    await navBarPage.goToEntity('product');
    productComponentsPage = new ProductComponentsPage();
    await browser.wait(ec.visibilityOf(productComponentsPage.title), 5000);
    expect(await productComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseProduct.home.title');
    await browser.wait(ec.or(ec.visibilityOf(productComponentsPage.entities), ec.visibilityOf(productComponentsPage.noResult)), 1000);
  });

  it('should load create Product page', async () => {
    await productComponentsPage.clickOnCreateButton();
    productUpdatePage = new ProductUpdatePage();
    expect(await productUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseProduct.home.createOrEditLabel');
    await productUpdatePage.cancel();
  });

  it('should create and save Products', async () => {
    const nbButtonsBeforeCreate = await productComponentsPage.countDeleteButtons();

    await productComponentsPage.clickOnCreateButton();

    await promise.all([
      productUpdatePage.setCompanyIDInput('5'),
      productUpdatePage.setReferenceInput('reference'),
      productUpdatePage.setReferenceProviderInput('referenceProvider'),
      productUpdatePage.setNameInput('name'),
      productUpdatePage.setQuantityInput('5'),
      productUpdatePage.setPurchaseUnitPriceInput('5'),
      productUpdatePage.setSaleUnitPriceInput('5'),
      productUpdatePage.setStocklimitInput('5'),
      productUpdatePage.unitTypeSelectLastOption(),
      productUpdatePage.sizeSelectLastOption(),
      productUpdatePage.setColorInput('color'),
      productUpdatePage.setImageInput(absolutePath),
      productUpdatePage.productCategorySelectLastOption(),
      productUpdatePage.productMarkSelectLastOption(),
      productUpdatePage.depotSelectLastOption(),
    ]);

    expect(await productUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');
    expect(await productUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');
    expect(await productUpdatePage.getReferenceProviderInput()).to.eq(
      'referenceProvider',
      'Expected ReferenceProvider value to be equals to referenceProvider'
    );
    expect(await productUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await productUpdatePage.getPurchaseUnitPriceInput()).to.eq('5', 'Expected purchaseUnitPrice value to be equals to 5');
    expect(await productUpdatePage.getSaleUnitPriceInput()).to.eq('5', 'Expected saleUnitPrice value to be equals to 5');
    expect(await productUpdatePage.getStocklimitInput()).to.eq('5', 'Expected stocklimit value to be equals to 5');
    const selectedStocklimitAlert = productUpdatePage.getStocklimitAlertInput();
    if (await selectedStocklimitAlert.isSelected()) {
      await productUpdatePage.getStocklimitAlertInput().click();
      expect(await productUpdatePage.getStocklimitAlertInput().isSelected(), 'Expected stocklimitAlert not to be selected').to.be.false;
    } else {
      await productUpdatePage.getStocklimitAlertInput().click();
      expect(await productUpdatePage.getStocklimitAlertInput().isSelected(), 'Expected stocklimitAlert to be selected').to.be.true;
    }
    expect(await productUpdatePage.getColorInput()).to.eq('color', 'Expected Color value to be equals to color');
    expect(await productUpdatePage.getImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected Image value to be end with ' + fileNameToUpload
    );
    const selectedIsDisplayedInCashier = productUpdatePage.getIsDisplayedInCashierInput();
    if (await selectedIsDisplayedInCashier.isSelected()) {
      await productUpdatePage.getIsDisplayedInCashierInput().click();
      expect(await productUpdatePage.getIsDisplayedInCashierInput().isSelected(), 'Expected isDisplayedInCashier not to be selected').to.be
        .false;
    } else {
      await productUpdatePage.getIsDisplayedInCashierInput().click();
      expect(await productUpdatePage.getIsDisplayedInCashierInput().isSelected(), 'Expected isDisplayedInCashier to be selected').to.be
        .true;
    }

    await productUpdatePage.save();
    expect(await productUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Product', async () => {
    const nbButtonsBeforeDelete = await productComponentsPage.countDeleteButtons();
    await productComponentsPage.clickOnLastDeleteButton();

    productDeleteDialog = new ProductDeleteDialog();
    expect(await productDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseProduct.delete.question');
    await productDeleteDialog.clickOnConfirmButton();

    expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
