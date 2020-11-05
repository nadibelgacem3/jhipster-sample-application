import { element, by, ElementFinder } from 'protractor';

export class CashierCostumerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cashier-costumer div table .btn-danger'));
  title = element.all(by.css('jhi-cashier-costumer div h2#page-heading span')).first();
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

export class CashierCostumerUpdatePage {
  pageTitle = element(by.id('jhi-cashier-costumer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  referenceInput = element(by.id('field_reference'));
  firstNameInput = element(by.id('field_firstName'));
  lastNameInput = element(by.id('field_lastName'));
  phone1Input = element(by.id('field_phone1'));
  phone2Input = element(by.id('field_phone2'));
  imageInput = element(by.id('file_image'));
  emailInput = element(by.id('field_email'));

  cashierLocationSelect = element(by.id('field_cashierLocation'));
  cashierSelect = element(by.id('field_cashier'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setReferenceInput(reference: string): Promise<void> {
    await this.referenceInput.sendKeys(reference);
  }

  async getReferenceInput(): Promise<string> {
    return await this.referenceInput.getAttribute('value');
  }

  async setFirstNameInput(firstName: string): Promise<void> {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput(): Promise<string> {
    return await this.firstNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName: string): Promise<void> {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput(): Promise<string> {
    return await this.lastNameInput.getAttribute('value');
  }

  async setPhone1Input(phone1: string): Promise<void> {
    await this.phone1Input.sendKeys(phone1);
  }

  async getPhone1Input(): Promise<string> {
    return await this.phone1Input.getAttribute('value');
  }

  async setPhone2Input(phone2: string): Promise<void> {
    await this.phone2Input.sendKeys(phone2);
  }

  async getPhone2Input(): Promise<string> {
    return await this.phone2Input.getAttribute('value');
  }

  async setImageInput(image: string): Promise<void> {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput(): Promise<string> {
    return await this.imageInput.getAttribute('value');
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
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

export class CashierCostumerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cashierCostumer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cashierCostumer'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
