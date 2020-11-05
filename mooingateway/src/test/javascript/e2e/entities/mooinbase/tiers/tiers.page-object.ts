import { element, by, ElementFinder } from 'protractor';

export class TiersComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tiers div table .btn-danger'));
  title = element.all(by.css('jhi-tiers div h2#page-heading span')).first();
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

export class TiersUpdatePage {
  pageTitle = element(by.id('jhi-tiers-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  referenceInput = element(by.id('field_reference'));
  firstNameInput = element(by.id('field_firstName'));
  lastNameInput = element(by.id('field_lastName'));
  phone1Input = element(by.id('field_phone1'));
  phone2Input = element(by.id('field_phone2'));
  imageInput = element(by.id('file_image'));
  emailInput = element(by.id('field_email'));
  typeSelect = element(by.id('field_type'));
  isCustomerInput = element(by.id('field_isCustomer'));
  isSupplierInput = element(by.id('field_isSupplier'));
  companyIDInput = element(by.id('field_companyID'));

  tiersLocationSelect = element(by.id('field_tiersLocation'));

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

  async setTypeSelect(type: string): Promise<void> {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect.all(by.tagName('option')).last().click();
  }

  getIsCustomerInput(): ElementFinder {
    return this.isCustomerInput;
  }

  getIsSupplierInput(): ElementFinder {
    return this.isSupplierInput;
  }

  async setCompanyIDInput(companyID: string): Promise<void> {
    await this.companyIDInput.sendKeys(companyID);
  }

  async getCompanyIDInput(): Promise<string> {
    return await this.companyIDInput.getAttribute('value');
  }

  async tiersLocationSelectLastOption(): Promise<void> {
    await this.tiersLocationSelect.all(by.tagName('option')).last().click();
  }

  async tiersLocationSelectOption(option: string): Promise<void> {
    await this.tiersLocationSelect.sendKeys(option);
  }

  getTiersLocationSelect(): ElementFinder {
    return this.tiersLocationSelect;
  }

  async getTiersLocationSelectedOption(): Promise<string> {
    return await this.tiersLocationSelect.element(by.css('option:checked')).getText();
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

export class TiersDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tiers-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tiers'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
