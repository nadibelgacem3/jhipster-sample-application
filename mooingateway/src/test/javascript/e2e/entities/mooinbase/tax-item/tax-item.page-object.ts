import { element, by, ElementFinder } from 'protractor';

export class TaxItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tax-item div table .btn-danger'));
  title = element.all(by.css('jhi-tax-item div h2#page-heading span')).first();
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

export class TaxItemUpdatePage {
  pageTitle = element(by.id('jhi-tax-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  isValuedInput = element(by.id('field_isValued'));
  isPercentageInput = element(by.id('field_isPercentage'));
  valueInput = element(by.id('field_value'));
  companyIDInput = element(by.id('field_companyID'));
  taxIDInput = element(by.id('field_taxID'));

  productSelect = element(by.id('field_product'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  getIsValuedInput(): ElementFinder {
    return this.isValuedInput;
  }

  getIsPercentageInput(): ElementFinder {
    return this.isPercentageInput;
  }

  async setValueInput(value: string): Promise<void> {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput(): Promise<string> {
    return await this.valueInput.getAttribute('value');
  }

  async setCompanyIDInput(companyID: string): Promise<void> {
    await this.companyIDInput.sendKeys(companyID);
  }

  async getCompanyIDInput(): Promise<string> {
    return await this.companyIDInput.getAttribute('value');
  }

  async setTaxIDInput(taxID: string): Promise<void> {
    await this.taxIDInput.sendKeys(taxID);
  }

  async getTaxIDInput(): Promise<string> {
    return await this.taxIDInput.getAttribute('value');
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

export class TaxItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-taxItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-taxItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
