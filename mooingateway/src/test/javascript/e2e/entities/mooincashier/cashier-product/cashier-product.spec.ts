import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { CashierProductComponentsPage, CashierProductDeleteDialog, CashierProductUpdatePage } from './cashier-product.page-object';

const expect = chai.expect;

describe('CashierProduct e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cashierProductComponentsPage: CashierProductComponentsPage;
  let cashierProductUpdatePage: CashierProductUpdatePage;
  let cashierProductDeleteDialog: CashierProductDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CashierProducts', async () => {
    await navBarPage.goToEntity('cashier-product');
    cashierProductComponentsPage = new CashierProductComponentsPage();
    await browser.wait(ec.visibilityOf(cashierProductComponentsPage.title), 5000);
    expect(await cashierProductComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincashierCashierProduct.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(cashierProductComponentsPage.entities), ec.visibilityOf(cashierProductComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CashierProduct page', async () => {
    await cashierProductComponentsPage.clickOnCreateButton();
    cashierProductUpdatePage = new CashierProductUpdatePage();
    expect(await cashierProductUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincashierCashierProduct.home.createOrEditLabel');
    await cashierProductUpdatePage.cancel();
  });

  it('should create and save CashierProducts', async () => {
    const nbButtonsBeforeCreate = await cashierProductComponentsPage.countDeleteButtons();

    await cashierProductComponentsPage.clickOnCreateButton();

    await promise.all([
      cashierProductUpdatePage.setProductIDInput('5'),
      cashierProductUpdatePage.setCashierProdNameInput('cashierProdName'),
      cashierProductUpdatePage.setCashierProdQtyInput('5'),
      cashierProductUpdatePage.setCashierProdPurchaseUnitPriceInput('5'),
      cashierProductUpdatePage.setCashierProdSaleUnitPriceInput('5'),
      cashierProductUpdatePage.setCashierProdStockLimitInput('5'),
    ]);

    expect(await cashierProductUpdatePage.getProductIDInput()).to.eq('5', 'Expected productID value to be equals to 5');
    expect(await cashierProductUpdatePage.getCashierProdNameInput()).to.eq(
      'cashierProdName',
      'Expected CashierProdName value to be equals to cashierProdName'
    );
    expect(await cashierProductUpdatePage.getCashierProdQtyInput()).to.eq('5', 'Expected cashierProdQty value to be equals to 5');
    expect(await cashierProductUpdatePage.getCashierProdPurchaseUnitPriceInput()).to.eq(
      '5',
      'Expected cashierProdPurchaseUnitPrice value to be equals to 5'
    );
    expect(await cashierProductUpdatePage.getCashierProdSaleUnitPriceInput()).to.eq(
      '5',
      'Expected cashierProdSaleUnitPrice value to be equals to 5'
    );
    expect(await cashierProductUpdatePage.getCashierProdStockLimitInput()).to.eq(
      '5',
      'Expected cashierProdStockLimit value to be equals to 5'
    );
    const selectedCashierProdStockLimitAlert = cashierProductUpdatePage.getCashierProdStockLimitAlertInput();
    if (await selectedCashierProdStockLimitAlert.isSelected()) {
      await cashierProductUpdatePage.getCashierProdStockLimitAlertInput().click();
      expect(
        await cashierProductUpdatePage.getCashierProdStockLimitAlertInput().isSelected(),
        'Expected cashierProdStockLimitAlert not to be selected'
      ).to.be.false;
    } else {
      await cashierProductUpdatePage.getCashierProdStockLimitAlertInput().click();
      expect(
        await cashierProductUpdatePage.getCashierProdStockLimitAlertInput().isSelected(),
        'Expected cashierProdStockLimitAlert to be selected'
      ).to.be.true;
    }

    await cashierProductUpdatePage.save();
    expect(await cashierProductUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cashierProductComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CashierProduct', async () => {
    const nbButtonsBeforeDelete = await cashierProductComponentsPage.countDeleteButtons();
    await cashierProductComponentsPage.clickOnLastDeleteButton();

    cashierProductDeleteDialog = new CashierProductDeleteDialog();
    expect(await cashierProductDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincashierCashierProduct.delete.question');
    await cashierProductDeleteDialog.clickOnConfirmButton();

    expect(await cashierProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
