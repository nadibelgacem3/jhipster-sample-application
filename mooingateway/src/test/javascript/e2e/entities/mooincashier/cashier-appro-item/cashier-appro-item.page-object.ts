import { element, by, ElementFinder } from 'protractor';

export class CashierApproItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cashier-appro-item div table .btn-danger'));
  title = element.all(by.css('jhi-cashier-appro-item div h2#page-heading span')).first();
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

export class CashierApproItemUpdatePage {
  pageTitle = element(by.id('jhi-cashier-appro-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  quantityInput = element(by.id('field_quantity'));
  discountRateInput = element(by.id('field_discountRate'));
  totalHTInput = element(by.id('field_totalHT'));
  totalTVAInput = element(by.id('field_totalTVA'));
  totaTTCInput = element(by.id('field_totaTTC'));

  cashierProductSelect = element(by.id('field_cashierProduct'));
  cashierApproSelect = element(by.id('field_cashierAppro'));

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

  async cashierProductSelectLastOption(): Promise<void> {
    await this.cashierProductSelect.all(by.tagName('option')).last().click();
  }

  async cashierProductSelectOption(option: string): Promise<void> {
    await this.cashierProductSelect.sendKeys(option);
  }

  getCashierProductSelect(): ElementFinder {
    return this.cashierProductSelect;
  }

  async getCashierProductSelectedOption(): Promise<string> {
    return await this.cashierProductSelect.element(by.css('option:checked')).getText();
  }

  async cashierApproSelectLastOption(): Promise<void> {
    await this.cashierApproSelect.all(by.tagName('option')).last().click();
  }

  async cashierApproSelectOption(option: string): Promise<void> {
    await this.cashierApproSelect.sendKeys(option);
  }

  getCashierApproSelect(): ElementFinder {
    return this.cashierApproSelect;
  }

  async getCashierApproSelectedOption(): Promise<string> {
    return await this.cashierApproSelect.element(by.css('option:checked')).getText();
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

export class CashierApproItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cashierApproItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cashierApproItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
