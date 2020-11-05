import { element, by, ElementFinder } from 'protractor';

export class TiersBankCheckComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tiers-bank-check div table .btn-danger'));
  title = element.all(by.css('jhi-tiers-bank-check div h2#page-heading span')).first();
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

export class TiersBankCheckUpdatePage {
  pageTitle = element(by.id('jhi-tiers-bank-check-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  bankNameInput = element(by.id('field_bankName'));
  numberInput = element(by.id('field_number'));
  amountInput = element(by.id('field_amount'));
  dueDateInput = element(by.id('field_dueDate'));
  bankCheckTypeSelect = element(by.id('field_bankCheckType'));
  bankCheckKindSelect = element(by.id('field_bankCheckKind'));
  swiftInput = element(by.id('field_swift'));
  typeInput = element(by.id('field_type'));

  tiersSelect = element(by.id('field_tiers'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setBankNameInput(bankName: string): Promise<void> {
    await this.bankNameInput.sendKeys(bankName);
  }

  async getBankNameInput(): Promise<string> {
    return await this.bankNameInput.getAttribute('value');
  }

  async setNumberInput(number: string): Promise<void> {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput(): Promise<string> {
    return await this.numberInput.getAttribute('value');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async setDueDateInput(dueDate: string): Promise<void> {
    await this.dueDateInput.sendKeys(dueDate);
  }

  async getDueDateInput(): Promise<string> {
    return await this.dueDateInput.getAttribute('value');
  }

  async setBankCheckTypeSelect(bankCheckType: string): Promise<void> {
    await this.bankCheckTypeSelect.sendKeys(bankCheckType);
  }

  async getBankCheckTypeSelect(): Promise<string> {
    return await this.bankCheckTypeSelect.element(by.css('option:checked')).getText();
  }

  async bankCheckTypeSelectLastOption(): Promise<void> {
    await this.bankCheckTypeSelect.all(by.tagName('option')).last().click();
  }

  async setBankCheckKindSelect(bankCheckKind: string): Promise<void> {
    await this.bankCheckKindSelect.sendKeys(bankCheckKind);
  }

  async getBankCheckKindSelect(): Promise<string> {
    return await this.bankCheckKindSelect.element(by.css('option:checked')).getText();
  }

  async bankCheckKindSelectLastOption(): Promise<void> {
    await this.bankCheckKindSelect.all(by.tagName('option')).last().click();
  }

  async setSwiftInput(swift: string): Promise<void> {
    await this.swiftInput.sendKeys(swift);
  }

  async getSwiftInput(): Promise<string> {
    return await this.swiftInput.getAttribute('value');
  }

  async setTypeInput(type: string): Promise<void> {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput(): Promise<string> {
    return await this.typeInput.getAttribute('value');
  }

  async tiersSelectLastOption(): Promise<void> {
    await this.tiersSelect.all(by.tagName('option')).last().click();
  }

  async tiersSelectOption(option: string): Promise<void> {
    await this.tiersSelect.sendKeys(option);
  }

  getTiersSelect(): ElementFinder {
    return this.tiersSelect;
  }

  async getTiersSelectedOption(): Promise<string> {
    return await this.tiersSelect.element(by.css('option:checked')).getText();
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

export class TiersBankCheckDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tiersBankCheck-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tiersBankCheck'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
