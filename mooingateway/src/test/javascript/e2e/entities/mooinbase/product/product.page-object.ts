import { element, by, ElementFinder } from 'protractor';

export class ProductComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product div table .btn-danger'));
  title = element.all(by.css('jhi-product div h2#page-heading span')).first();
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

export class ProductUpdatePage {
  pageTitle = element(by.id('jhi-product-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  companyIDInput = element(by.id('field_companyID'));
  referenceInput = element(by.id('field_reference'));
  referenceProviderInput = element(by.id('field_referenceProvider'));
  nameInput = element(by.id('field_name'));
  quantityInput = element(by.id('field_quantity'));
  purchaseUnitPriceInput = element(by.id('field_purchaseUnitPrice'));
  saleUnitPriceInput = element(by.id('field_saleUnitPrice'));
  stocklimitInput = element(by.id('field_stocklimit'));
  stocklimitAlertInput = element(by.id('field_stocklimitAlert'));
  unitTypeSelect = element(by.id('field_unitType'));
  sizeSelect = element(by.id('field_size'));
  colorInput = element(by.id('field_color'));
  imageInput = element(by.id('file_image'));
  isDisplayedInCashierInput = element(by.id('field_isDisplayedInCashier'));

  productCategorySelect = element(by.id('field_productCategory'));
  productMarkSelect = element(by.id('field_productMark'));
  depotSelect = element(by.id('field_depot'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCompanyIDInput(companyID: string): Promise<void> {
    await this.companyIDInput.sendKeys(companyID);
  }

  async getCompanyIDInput(): Promise<string> {
    return await this.companyIDInput.getAttribute('value');
  }

  async setReferenceInput(reference: string): Promise<void> {
    await this.referenceInput.sendKeys(reference);
  }

  async getReferenceInput(): Promise<string> {
    return await this.referenceInput.getAttribute('value');
  }

  async setReferenceProviderInput(referenceProvider: string): Promise<void> {
    await this.referenceProviderInput.sendKeys(referenceProvider);
  }

  async getReferenceProviderInput(): Promise<string> {
    return await this.referenceProviderInput.getAttribute('value');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
  }

  async setPurchaseUnitPriceInput(purchaseUnitPrice: string): Promise<void> {
    await this.purchaseUnitPriceInput.sendKeys(purchaseUnitPrice);
  }

  async getPurchaseUnitPriceInput(): Promise<string> {
    return await this.purchaseUnitPriceInput.getAttribute('value');
  }

  async setSaleUnitPriceInput(saleUnitPrice: string): Promise<void> {
    await this.saleUnitPriceInput.sendKeys(saleUnitPrice);
  }

  async getSaleUnitPriceInput(): Promise<string> {
    return await this.saleUnitPriceInput.getAttribute('value');
  }

  async setStocklimitInput(stocklimit: string): Promise<void> {
    await this.stocklimitInput.sendKeys(stocklimit);
  }

  async getStocklimitInput(): Promise<string> {
    return await this.stocklimitInput.getAttribute('value');
  }

  getStocklimitAlertInput(): ElementFinder {
    return this.stocklimitAlertInput;
  }

  async setUnitTypeSelect(unitType: string): Promise<void> {
    await this.unitTypeSelect.sendKeys(unitType);
  }

  async getUnitTypeSelect(): Promise<string> {
    return await this.unitTypeSelect.element(by.css('option:checked')).getText();
  }

  async unitTypeSelectLastOption(): Promise<void> {
    await this.unitTypeSelect.all(by.tagName('option')).last().click();
  }

  async setSizeSelect(size: string): Promise<void> {
    await this.sizeSelect.sendKeys(size);
  }

  async getSizeSelect(): Promise<string> {
    return await this.sizeSelect.element(by.css('option:checked')).getText();
  }

  async sizeSelectLastOption(): Promise<void> {
    await this.sizeSelect.all(by.tagName('option')).last().click();
  }

  async setColorInput(color: string): Promise<void> {
    await this.colorInput.sendKeys(color);
  }

  async getColorInput(): Promise<string> {
    return await this.colorInput.getAttribute('value');
  }

  async setImageInput(image: string): Promise<void> {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput(): Promise<string> {
    return await this.imageInput.getAttribute('value');
  }

  getIsDisplayedInCashierInput(): ElementFinder {
    return this.isDisplayedInCashierInput;
  }

  async productCategorySelectLastOption(): Promise<void> {
    await this.productCategorySelect.all(by.tagName('option')).last().click();
  }

  async productCategorySelectOption(option: string): Promise<void> {
    await this.productCategorySelect.sendKeys(option);
  }

  getProductCategorySelect(): ElementFinder {
    return this.productCategorySelect;
  }

  async getProductCategorySelectedOption(): Promise<string> {
    return await this.productCategorySelect.element(by.css('option:checked')).getText();
  }

  async productMarkSelectLastOption(): Promise<void> {
    await this.productMarkSelect.all(by.tagName('option')).last().click();
  }

  async productMarkSelectOption(option: string): Promise<void> {
    await this.productMarkSelect.sendKeys(option);
  }

  getProductMarkSelect(): ElementFinder {
    return this.productMarkSelect;
  }

  async getProductMarkSelectedOption(): Promise<string> {
    return await this.productMarkSelect.element(by.css('option:checked')).getText();
  }

  async depotSelectLastOption(): Promise<void> {
    await this.depotSelect.all(by.tagName('option')).last().click();
  }

  async depotSelectOption(option: string): Promise<void> {
    await this.depotSelect.sendKeys(option);
  }

  getDepotSelect(): ElementFinder {
    return this.depotSelect;
  }

  async getDepotSelectedOption(): Promise<string> {
    return await this.depotSelect.element(by.css('option:checked')).getText();
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

export class ProductDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-product-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-product'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
