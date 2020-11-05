import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  CashierReceiptItemComponentsPage,
  CashierReceiptItemDeleteDialog,
  CashierReceiptItemUpdatePage,
} from './cashier-receipt-item.page-object';

const expect = chai.expect;

describe('CashierReceiptItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cashierReceiptItemComponentsPage: CashierReceiptItemComponentsPage;
  let cashierReceiptItemUpdatePage: CashierReceiptItemUpdatePage;
  let cashierReceiptItemDeleteDialog: CashierReceiptItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CashierReceiptItems', async () => {
    await navBarPage.goToEntity('cashier-receipt-item');
    cashierReceiptItemComponentsPage = new CashierReceiptItemComponentsPage();
    await browser.wait(ec.visibilityOf(cashierReceiptItemComponentsPage.title), 5000);
    expect(await cashierReceiptItemComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincashierCashierReceiptItem.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(cashierReceiptItemComponentsPage.entities), ec.visibilityOf(cashierReceiptItemComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CashierReceiptItem page', async () => {
    await cashierReceiptItemComponentsPage.clickOnCreateButton();
    cashierReceiptItemUpdatePage = new CashierReceiptItemUpdatePage();
    expect(await cashierReceiptItemUpdatePage.getPageTitle()).to.eq(
      'mooingatewayApp.mooincashierCashierReceiptItem.home.createOrEditLabel'
    );
    await cashierReceiptItemUpdatePage.cancel();
  });

  it('should create and save CashierReceiptItems', async () => {
    const nbButtonsBeforeCreate = await cashierReceiptItemComponentsPage.countDeleteButtons();

    await cashierReceiptItemComponentsPage.clickOnCreateButton();

    await promise.all([
      cashierReceiptItemUpdatePage.setQuantityInput('5'),
      cashierReceiptItemUpdatePage.setDiscountRateInput('5'),
      cashierReceiptItemUpdatePage.setTotalHTInput('5'),
      cashierReceiptItemUpdatePage.setTotalTVAInput('5'),
      cashierReceiptItemUpdatePage.setTotaTTCInput('5'),
      cashierReceiptItemUpdatePage.cashierProductSelectLastOption(),
      cashierReceiptItemUpdatePage.cashierReceiptSelectLastOption(),
    ]);

    expect(await cashierReceiptItemUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await cashierReceiptItemUpdatePage.getDiscountRateInput()).to.eq('5', 'Expected discountRate value to be equals to 5');
    expect(await cashierReceiptItemUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await cashierReceiptItemUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await cashierReceiptItemUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');

    await cashierReceiptItemUpdatePage.save();
    expect(await cashierReceiptItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cashierReceiptItemComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CashierReceiptItem', async () => {
    const nbButtonsBeforeDelete = await cashierReceiptItemComponentsPage.countDeleteButtons();
    await cashierReceiptItemComponentsPage.clickOnLastDeleteButton();

    cashierReceiptItemDeleteDialog = new CashierReceiptItemDeleteDialog();
    expect(await cashierReceiptItemDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincashierCashierReceiptItem.delete.question');
    await cashierReceiptItemDeleteDialog.clickOnConfirmButton();

    expect(await cashierReceiptItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
