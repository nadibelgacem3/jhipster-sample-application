import { element, by, ElementFinder } from 'protractor';

export class CashierReceiptComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cashier-receipt div table .btn-danger'));
  title = element.all(by.css('jhi-cashier-receipt div h2#page-heading span')).first();
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

export class CashierReceiptUpdatePage {
  pageTitle = element(by.id('jhi-cashier-receipt-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  numberInput = element(by.id('field_number'));
  referenceInput = element(by.id('field_reference'));
  statusSelect = element(by.id('field_status'));
  totalHTInput = element(by.id('field_totalHT'));
  totalTVAInput = element(by.id('field_totalTVA'));
  totaTTCInput = element(by.id('field_totaTTC'));
  dateInput = element(by.id('field_date'));
  dueDateInput = element(by.id('field_dueDate'));
  isConvertedInput = element(by.id('field_isConverted'));
  withTVAInput = element(by.id('field_withTVA'));
  withTaxInput = element(by.id('field_withTax'));

  cashierSelect = element(by.id('field_cashier'));
  cashierCostumerSelect = element(by.id('field_cashierCostumer'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumberInput(number: string): Promise<void> {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput(): Promise<string> {
    return await this.numberInput.getAttribute('value');
  }

  async setReferenceInput(reference: string): Promise<void> {
    await this.referenceInput.sendKeys(reference);
  }

  async getReferenceInput(): Promise<string> {
    return await this.referenceInput.getAttribute('value');
  }

  async setStatusSelect(status: string): Promise<void> {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
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

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  async setDueDateInput(dueDate: string): Promise<void> {
    await this.dueDateInput.sendKeys(dueDate);
  }

  async getDueDateInput(): Promise<string> {
    return await this.dueDateInput.getAttribute('value');
  }

  getIsConvertedInput(): ElementFinder {
    return this.isConvertedInput;
  }

  getWithTVAInput(): ElementFinder {
    return this.withTVAInput;
  }

  getWithTaxInput(): ElementFinder {
    return this.withTaxInput;
  }

  async cashierSelectLastOption(): Promise<void> {
    await this.cashierSelect.all(by.tagName('option')).last().click();
  }

  async cashierSelectOption(option: string): Promise<void> {
    await this.cashierSelect.sendKeys(option);
  }

  getCashierSelect(): ElementFinder {
    return this.cashierSelect;
  }

  async getCashierSelectedOption(): Promise<string> {
    return await this.cashierSelect.element(by.css('option:checked')).getText();
  }

  async cashierCostumerSelectLastOption(): Promise<void> {
    await this.cashierCostumerSelect.all(by.tagName('option')).last().click();
  }

  async cashierCostumerSelectOption(option: string): Promise<void> {
    await this.cashierCostumerSelect.sendKeys(option);
  }

  getCashierCostumerSelect(): ElementFinder {
    return this.cashierCostumerSelect;
  }

  async getCashierCostumerSelectedOption(): Promise<string> {
    return await this.cashierCostumerSelect.element(by.css('option:checked')).getText();
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

export class CashierReceiptDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cashierReceipt-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cashierReceipt'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
