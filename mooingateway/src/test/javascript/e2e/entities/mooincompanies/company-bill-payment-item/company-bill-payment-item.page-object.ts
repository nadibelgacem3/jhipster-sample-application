import { element, by, ElementFinder } from 'protractor';

export class CompanyBillPaymentItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-company-bill-payment-item div table .btn-danger'));
  title = element.all(by.css('jhi-company-bill-payment-item div h2#page-heading span')).first();
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

export class CompanyBillPaymentItemUpdatePage {
  pageTitle = element(by.id('jhi-company-bill-payment-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  quantityInput = element(by.id('field_quantity'));
  discountRateInput = element(by.id('field_discountRate'));
  totalHTInput = element(by.id('field_totalHT'));
  totalTVAInput = element(by.id('field_totalTVA'));
  totaTTCInput = element(by.id('field_totaTTC'));

  companyBillPaymentSelect = element(by.id('field_companyBillPayment'));
  companyModuleSelect = element(by.id('field_companyModule'));

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

  async companyBillPaymentSelectLastOption(): Promise<void> {
    await this.companyBillPaymentSelect.all(by.tagName('option')).last().click();
  }

  async companyBillPaymentSelectOption(option: string): Promise<void> {
    await this.companyBillPaymentSelect.sendKeys(option);
  }

  getCompanyBillPaymentSelect(): ElementFinder {
    return this.companyBillPaymentSelect;
  }

  async getCompanyBillPaymentSelectedOption(): Promise<string> {
    return await this.companyBillPaymentSelect.element(by.css('option:checked')).getText();
  }

  async companyModuleSelectLastOption(): Promise<void> {
    await this.companyModuleSelect.all(by.tagName('option')).last().click();
  }

  async companyModuleSelectOption(option: string): Promise<void> {
    await this.companyModuleSelect.sendKeys(option);
  }

  getCompanyModuleSelect(): ElementFinder {
    return this.companyModuleSelect;
  }

  async getCompanyModuleSelectedOption(): Promise<string> {
    return await this.companyModuleSelect.element(by.css('option:checked')).getText();
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

export class CompanyBillPaymentItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-companyBillPaymentItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-companyBillPaymentItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
