import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ProductMarkComponentsPage, ProductMarkDeleteDialog, ProductMarkUpdatePage } from './product-mark.page-object';

const expect = chai.expect;

describe('ProductMark e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productMarkComponentsPage: ProductMarkComponentsPage;
  let productMarkUpdatePage: ProductMarkUpdatePage;
  let productMarkDeleteDialog: ProductMarkDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductMarks', async () => {
    await navBarPage.goToEntity('product-mark');
    productMarkComponentsPage = new ProductMarkComponentsPage();
    await browser.wait(ec.visibilityOf(productMarkComponentsPage.title), 5000);
    expect(await productMarkComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseProductMark.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productMarkComponentsPage.entities), ec.visibilityOf(productMarkComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductMark page', async () => {
    await productMarkComponentsPage.clickOnCreateButton();
    productMarkUpdatePage = new ProductMarkUpdatePage();
    expect(await productMarkUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseProductMark.home.createOrEditLabel');
    await productMarkUpdatePage.cancel();
  });

  it('should create and save ProductMarks', async () => {
    const nbButtonsBeforeCreate = await productMarkComponentsPage.countDeleteButtons();

    await productMarkComponentsPage.clickOnCreateButton();

    await promise.all([
      productMarkUpdatePage.setNameInput('name'),
      productMarkUpdatePage.setDescriptionInput('description'),
      productMarkUpdatePage.setCompanyIDInput('5'),
    ]);

    expect(await productMarkUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productMarkUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await productMarkUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');

    await productMarkUpdatePage.save();
    expect(await productMarkUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productMarkComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ProductMark', async () => {
    const nbButtonsBeforeDelete = await productMarkComponentsPage.countDeleteButtons();
    await productMarkComponentsPage.clickOnLastDeleteButton();

    productMarkDeleteDialog = new ProductMarkDeleteDialog();
    expect(await productMarkDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseProductMark.delete.question');
    await productMarkDeleteDialog.clickOnConfirmButton();

    expect(await productMarkComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
