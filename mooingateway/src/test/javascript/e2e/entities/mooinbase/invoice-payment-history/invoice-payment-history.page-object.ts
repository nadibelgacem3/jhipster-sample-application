import { element, by, ElementFinder } from 'protractor';

export class InvoicePaymentHistoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-invoice-payment-history div table .btn-danger'));
  title = element.all(by.css('jhi-invoice-payment-history div h2#page-heading span')).first();
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

export class InvoicePaymentHistoryUpdatePage {
  pageTitle = element(by.id('jhi-invoice-payment-history-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  detailsInput = element(by.id('field_details'));
  amountInput = element(by.id('field_amount'));
  paymentDateInput = element(by.id('field_paymentDate'));
  paymentMethodSelect = element(by.id('field_paymentMethod'));
  bankCheckorTraitNumberInput = element(by.id('field_bankCheckorTraitNumber'));
  imageJustifInput = element(by.id('file_imageJustif'));

  invoiceSelect = element(by.id('field_invoice'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDetailsInput(details: string): Promise<void> {
    await this.detailsInput.sendKeys(details);
  }

  async getDetailsInput(): Promise<string> {
    return await this.detailsInput.getAttribute('value');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async setPaymentDateInput(paymentDate: string): Promise<void> {
    await this.paymentDateInput.sendKeys(paymentDate);
  }

  async getPaymentDateInput(): Promise<string> {
    return await this.paymentDateInput.getAttribute('value');
  }

  async setPaymentMethodSelect(paymentMethod: string): Promise<void> {
    await this.paymentMethodSelect.sendKeys(paymentMethod);
  }

  async getPaymentMethodSelect(): Promise<string> {
    return await this.paymentMethodSelect.element(by.css('option:checked')).getText();
  }

  async paymentMethodSelectLastOption(): Promise<void> {
    await this.paymentMethodSelect.all(by.tagName('option')).last().click();
  }

  async setBankCheckorTraitNumberInput(bankCheckorTraitNumber: string): Promise<void> {
    await this.bankCheckorTraitNumberInput.sendKeys(bankCheckorTraitNumber);
  }

  async getBankCheckorTraitNumberInput(): Promise<string> {
    return await this.bankCheckorTraitNumberInput.getAttribute('value');
  }

  async setImageJustifInput(imageJustif: string): Promise<void> {
    await this.imageJustifInput.sendKeys(imageJustif);
  }

  async getImageJustifInput(): Promise<string> {
    return await this.imageJustifInput.getAttribute('value');
  }

  async invoiceSelectLastOption(): Promise<void> {
    await this.invoiceSelect.all(by.tagName('option')).last().click();
  }

  async invoiceSelectOption(option: string): Promise<void> {
    await this.invoiceSelect.sendKeys(option);
  }

  getInvoiceSelect(): ElementFinder {
    return this.invoiceSelect;
  }

  async getInvoiceSelectedOption(): Promise<string> {
    return await this.invoiceSelect.element(by.css('option:checked')).getText();
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

export class InvoicePaymentHistoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-invoicePaymentHistory-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-invoicePaymentHistory'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
