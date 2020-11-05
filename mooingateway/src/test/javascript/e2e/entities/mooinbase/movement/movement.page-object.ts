import { element, by, ElementFinder } from 'protractor';

export class MovementComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-movement div table .btn-danger'));
  title = element.all(by.css('jhi-movement div h2#page-heading span')).first();
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

export class MovementUpdatePage {
  pageTitle = element(by.id('jhi-movement-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  typeSelect = element(by.id('field_type'));
  reasonSelect = element(by.id('field_reason'));
  dateInput = element(by.id('field_date'));
  billIDInput = element(by.id('field_billID'));
  tiersIDInput = element(by.id('field_tiersID'));
  quantityInput = element(by.id('field_quantity'));
  companyUserIDInput = element(by.id('field_companyUserID'));

  productSelect = element(by.id('field_product'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async setReasonSelect(reason: string): Promise<void> {
    await this.reasonSelect.sendKeys(reason);
  }

  async getReasonSelect(): Promise<string> {
    return await this.reasonSelect.element(by.css('option:checked')).getText();
  }

  async reasonSelectLastOption(): Promise<void> {
    await this.reasonSelect.all(by.tagName('option')).last().click();
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  async setBillIDInput(billID: string): Promise<void> {
    await this.billIDInput.sendKeys(billID);
  }

  async getBillIDInput(): Promise<string> {
    return await this.billIDInput.getAttribute('value');
  }

  async setTiersIDInput(tiersID: string): Promise<void> {
    await this.tiersIDInput.sendKeys(tiersID);
  }

  async getTiersIDInput(): Promise<string> {
    return await this.tiersIDInput.getAttribute('value');
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
  }

  async setCompanyUserIDInput(companyUserID: string): Promise<void> {
    await this.companyUserIDInput.sendKeys(companyUserID);
  }

  async getCompanyUserIDInput(): Promise<string> {
    return await this.companyUserIDInput.getAttribute('value');
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

export class MovementDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-movement-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-movement'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
