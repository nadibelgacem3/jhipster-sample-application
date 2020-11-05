import { element, by, ElementFinder } from 'protractor';

export class AvoirItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-avoir-item div table .btn-danger'));
  title = element.all(by.css('jhi-avoir-item div h2#page-heading span')).first();
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

export class AvoirItemUpdatePage {
  pageTitle = element(by.id('jhi-avoir-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  quantityInput = element(by.id('field_quantity'));
  discountRateInput = element(by.id('field_discountRate'));
  totalHTInput = element(by.id('field_totalHT'));
  totalTVAInput = element(by.id('field_totalTVA'));
  totaTTCInput = element(by.id('field_totaTTC'));

  productSelect = element(by.id('field_product'));
  avoirSelect = element(by.id('field_avoir'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
  }

  async setDiscountRateInput(discountRate: string): Promise<void> {
    await this.discountRateInput.sendKeys(discountRate);
  }

  async getDiscountRateInput(): Promise<string> {
    return await this.discountRateInput.getAttribute('value');
  }

  async setTotalHTInput(totalHT: string): Promise<void> {
    await this.totalHTInput.sendKeys(totalHT);
  }

  async getTotalHTInput(): Promise<string> {
    return await this.totalHTInput.getAttribute('value');
  }

  async setTotalTVAInput(totalTVA: string): Promise<void> {
    await this.totalTVAInput.sendKeys(totalTVA);
  }

  async getTotalTVAInput(): Promise<string> {
    return await this.totalTVAInput.getAttribute('value');
  }

  async setTotaTTCInput(totaTTC: string): Promise<void> {
    await this.totaTTCInput.sendKeys(totaTTC);
  }

  async getTotaTTCInput(): Promise<string> {
    return await this.totaTTCInput.getAttribute('value');
  }

  async productSelectLastOption(): Promise<void> {
    await this.productSelect.all(by.tagName('option')).last().click();
  }

  async productSelectOption(option: string): Promise<void> {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption(): Promise<string> {
    return await this.productSelect.element(by.css('option:checked')).getText();
  }

  async avoirSelectLastOption(): Promise<void> {
    await this.avoirSelect.all(by.tagName('option')).last().click();
  }

  async avoirSelectOption(option: string): Promise<void> {
    await this.avoirSelect.sendKeys(option);
  }

  getAvoirSelect(): ElementFinder {
    return this.avoirSelect;
  }

  async getAvoirSelectedOption(): Promise<string> {
    return await this.avoirSelect.element(by.css('option:checked')).getText();
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

export class AvoirItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-avoirItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-avoirItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
