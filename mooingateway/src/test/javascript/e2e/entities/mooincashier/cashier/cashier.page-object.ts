import { element, by, ElementFinder } from 'protractor';

export class CashierComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cashier div table .btn-danger'));
  title = element.all(by.css('jhi-cashier div h2#page-heading span')).first();
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

export class CashierUpdatePage {
  pageTitle = element(by.id('jhi-cashier-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  withTicketInput = element(by.id('field_withTicket'));
  withTVAInput = element(by.id('field_withTVA'));
  withTaxInput = element(by.id('field_withTax'));
  withApproInput = element(by.id('field_withAppro'));
  themeColorInput = element(by.id('field_themeColor'));
  isActivatedInput = element(by.id('field_isActivated'));
  companyIDInput = element(by.id('field_companyID'));

  cashierLocationSelect = element(by.id('field_cashierLocation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  getWithTicketInput(): ElementFinder {
    return this.withTicketInput;
  }

  getWithTVAInput(): ElementFinder {
    return this.withTVAInput;
  }

  getWithTaxInput(): ElementFinder {
    return this.withTaxInput;
  }

  getWithApproInput(): ElementFinder {
    return this.withApproInput;
  }

  async setThemeColorInput(themeColor: string): Promise<void> {
    await this.themeColorInput.sendKeys(themeColor);
  }

  async getThemeColorInput(): Promise<string> {
    return await this.themeColorInput.getAttribute('value');
  }

  getIsActivatedInput(): ElementFinder {
    return this.isActivatedInput;
  }

  async setCompanyIDInput(companyID: string): Promise<void> {
    await this.companyIDInput.sendKeys(companyID);
  }

  async getCompanyIDInput(): Promise<string> {
    return await this.companyIDInput.getAttribute('value');
  }

  async cashierLocationSelectLastOption(): Promise<void> {
    await this.cashierLocationSelect.all(by.tagName('option')).last().click();
  }

  async cashierLocationSelectOption(option: string): Promise<void> {
    await this.cashierLocationSelect.sendKeys(option);
  }

  getCashierLocationSelect(): ElementFinder {
    return this.cashierLocationSelect;
  }

  async getCashierLocationSelectedOption(): Promise<string> {
    return await this.cashierLocationSelect.element(by.css('option:checked')).getText();
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

export class CashierDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cashier-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cashier'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
