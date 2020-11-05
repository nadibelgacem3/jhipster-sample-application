import { element, by, ElementFinder } from 'protractor';

export class CashierProductComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cashier-product div table .btn-danger'));
  title = element.all(by.css('jhi-cashier-product div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class CashierProductUpdatePage {
  pageTitle = element(by.id('jhi-cashier-product-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  productIDInput = element(by.id('field_productID'));
  cashierProdNameInput = element(by.id('field_cashierProdName'));
  cashierProdQtyInput = element(by.id('field_cashierProdQty'));
  cashierProdPurchaseUnitPriceInput = element(by.id('field_cashierProdPurchaseUnitPrice'));
  cashierProdSaleUnitPriceInput = element(by.id('field_cashierProdSaleUnitPrice'));
  cashierProdStockLimitInput = element(by.id('field_cashierProdStockLimit'));
  cashierProdStockLimitAlertInput = element(by.id('field_cashierProdStockLimitAlert'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setProductIDInput(productID: string): Promise<void> {
    await this.productIDInput.sendKeys(productID);
  }

  async getProductIDInput(): Promise<string> {
    return await this.productIDInput.getAttribute('value');
  }

  async setCashierProdNameInput(cashierProdName: string): Promise<void> {
    await this.cashierProdNameInput.sendKeys(cashierProdName);
  }

  async getCashierProdNameInput(): Promise<string> {
    return await this.cashierProdNameInput.getAttribute('value');
  }

  async setCashierProdQtyInput(cashierProdQty: string): Promise<void> {
    await this.cashierProdQtyInput.sendKeys(cashierProdQty);
  }

  async getCashierProdQtyInput(): Promise<string> {
    return await this.cashierProdQtyInput.getAttribute('value');
  }

  async setCashierProdPurchaseUnitPriceInput(cashierProdPurchaseUnitPrice: string): Promise<void> {
    await this.cashierProdPurchaseUnitPriceInput.sendKeys(cashierProdPurchaseUnitPrice);
  }

  async getCashierProdPurchaseUnitPriceInput(): Promise<string> {
    return await this.cashierProdPurchaseUnitPriceInput.getAttribute('value');
  }

  async setCashierProdSaleUnitPriceInput(cashierProdSaleUnitPrice: string): Promise<void> {
    await this.cashierProdSaleUnitPriceInput.sendKeys(cashierProdSaleUnitPrice);
  }

  async getCashierProdSaleUnitPriceInput(): Promise<string> {
    return await this.cashierProdSaleUnitPriceInput.getAttribute('value');
  }

  async setCashierProdStockLimitInput(cashierProdStockLimit: string): Promise<void> {
    await this.cashierProdStockLimitInput.sendKeys(cashierProdStockLimit);
  }

  async getCashierProdStockLimitInput(): Promise<string> {
    return await this.cashierProdStockLimitInput.getAttribute('value');
  }

  getCashierProdStockLimitAlertInput(): ElementFinder {
    return this.cashierProdStockLimitAlertInput;
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CashierProductDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cashierProduct-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cashierProduct'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
