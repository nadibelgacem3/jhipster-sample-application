import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  CompanyBillPaymentItemComponentsPage,
  CompanyBillPaymentItemDeleteDialog,
  CompanyBillPaymentItemUpdatePage,
} from './company-bill-payment-item.page-object';

const expect = chai.expect;

describe('CompanyBillPaymentItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyBillPaymentItemComponentsPage: CompanyBillPaymentItemComponentsPage;
  let companyBillPaymentItemUpdatePage: CompanyBillPaymentItemUpdatePage;
  let companyBillPaymentItemDeleteDialog: CompanyBillPaymentItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CompanyBillPaymentItems', async () => {
    await navBarPage.goToEntity('company-bill-payment-item');
    companyBillPaymentItemComponentsPage = new CompanyBillPaymentItemComponentsPage();
    await browser.wait(ec.visibilityOf(companyBillPaymentItemComponentsPage.title), 5000);
    expect(await companyBillPaymentItemComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyBillPaymentItem.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(companyBillPaymentItemComponentsPage.entities), ec.visibilityOf(companyBillPaymentItemComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CompanyBillPaymentItem page', async () => {
    await companyBillPaymentItemComponentsPage.clickOnCreateButton();
    companyBillPaymentItemUpdatePage = new CompanyBillPaymentItemUpdatePage();
    expect(await companyBillPaymentItemUpdatePage.getPageTitle()).to.eq(
      'mooingatewayApp.mooincompaniesCompanyBillPaymentItem.home.createOrEditLabel'
    );
    await companyBillPaymentItemUpdatePage.cancel();
  });

  it('should create and save CompanyBillPaymentItems', async () => {
    const nbButtonsBeforeCreate = await companyBillPaymentItemComponentsPage.countDeleteButtons();

    await companyBillPaymentItemComponentsPage.clickOnCreateButton();

    await promise.all([
      companyBillPaymentItemUpdatePage.setQuantityInput('5'),
      companyBillPaymentItemUpdatePage.setDiscountRateInput('5'),
      companyBillPaymentItemUpdatePage.setTotalHTInput('5'),
      companyBillPaymentItemUpdatePage.setTotalTVAInput('5'),
      companyBillPaymentItemUpdatePage.setTotaTTCInput('5'),
      companyBillPaymentItemUpdatePage.companyBillPaymentSelectLastOption(),
      companyBillPaymentItemUpdatePage.companyModuleSelectLastOption(),
    ]);

    expect(await companyBillPaymentItemUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await companyBillPaymentItemUpdatePage.getDiscountRateInput()).to.eq('5', 'Expected discountRate value to be equals to 5');
    expect(await companyBillPaymentItemUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await companyBillPaymentItemUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await companyBillPaymentItemUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');

    await companyBillPaymentItemUpdatePage.save();
    expect(await companyBillPaymentItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await companyBillPaymentItemComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CompanyBillPaymentItem', async () => {
    const nbButtonsBeforeDelete = await companyBillPaymentItemComponentsPage.countDeleteButtons();
    await companyBillPaymentItemComponentsPage.clickOnLastDeleteButton();

    companyBillPaymentItemDeleteDialog = new CompanyBillPaymentItemDeleteDialog();
    expect(await companyBillPaymentItemDeleteDialog.getDialogTitle()).to.eq(
      'mooingatewayApp.mooincompaniesCompanyBillPaymentItem.delete.question'
    );
    await companyBillPaymentItemDeleteDialog.clickOnConfirmButton();

    expect(await companyBillPaymentItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
