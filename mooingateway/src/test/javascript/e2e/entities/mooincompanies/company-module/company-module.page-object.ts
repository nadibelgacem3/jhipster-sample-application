import { element, by, ElementFinder } from 'protractor';

export class CompanyModuleComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-company-module div table .btn-danger'));
  title = element.all(by.css('jhi-company-module div h2#page-heading span')).first();
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

export class CompanyModuleUpdatePage {
  pageTitle = element(by.id('jhi-company-module-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  activatedAtInput = element(by.id('field_activatedAt'));
  activatedUntilInput = element(by.id('field_activatedUntil'));
  isActivatedInput = element(by.id('field_isActivated'));
  priceInput = element(by.id('field_price'));

  companySelect = element(by.id('field_company'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setActivatedAtInput(activatedAt: string): Promise<void> {
    await this.activatedAtInput.sendKeys(activatedAt);
  }

  async getActivatedAtInput(): Promise<string> {
    return await this.activatedAtInput.getAttribute('value');
  }

  async setActivatedUntilInput(activatedUntil: string): Promise<void> {
    await this.activatedUntilInput.sendKeys(activatedUntil);
  }

  async getActivatedUntilInput(): Promise<string> {
    return await this.activatedUntilInput.getAttribute('value');
  }

  getIsActivatedInput(): ElementFinder {
    return this.isActivatedInput;
  }

  async setPriceInput(price: string): Promise<void> {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput(): Promise<string> {
    return await this.priceInput.getAttribute('value');
  }

  async companySelectLastOption(): Promise<void> {
    await this.companySelect.all(by.tagName('option')).last().click();
  }

  async companySelectOption(option: string): Promise<void> {
    await this.companySelect.sendKeys(option);
  }

  getCompanySelect(): ElementFinder {
    return this.companySelect;
  }

  async getCompanySelectedOption(): Promise<string> {
    return await this.companySelect.element(by.css('option:checked')).getText();
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

export class CompanyModuleDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-companyModule-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-companyModule'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
