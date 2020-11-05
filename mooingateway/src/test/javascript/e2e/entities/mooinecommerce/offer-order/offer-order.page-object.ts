import { element, by, ElementFinder } from 'protractor';

export class OfferOrderComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-offer-order div table .btn-danger'));
  title = element.all(by.css('jhi-offer-order div h2#page-heading span')).first();
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

export class OfferOrderUpdatePage {
  pageTitle = element(by.id('jhi-offer-order-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  quantityInput = element(by.id('field_quantity'));
  totalHTInput = element(by.id('field_totalHT'));
  totalTVAInput = element(by.id('field_totalTVA'));
  totaTTCInput = element(by.id('field_totaTTC'));
  dateInput = element(by.id('field_date'));
  statusSelect = element(by.id('field_status'));
  requesterOfferIDInput = element(by.id('field_requesterOfferID'));

  offerSelect = element(by.id('field_offer'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
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

  async setStatusSelect(status: string): Promise<void> {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async setRequesterOfferIDInput(requesterOfferID: string): Promise<void> {
    await this.requesterOfferIDInput.sendKeys(requesterOfferID);
  }

  async getRequesterOfferIDInput(): Promise<string> {
    return await this.requesterOfferIDInput.getAttribute('value');
  }

  async offerSelectLastOption(): Promise<void> {
    await this.offerSelect.all(by.tagName('option')).last().click();
  }

  async offerSelectOption(option: string): Promise<void> {
    await this.offerSelect.sendKeys(option);
  }

  getOfferSelect(): ElementFinder {
    return this.offerSelect;
  }

  async getOfferSelectedOption(): Promise<string> {
    return await this.offerSelect.element(by.css('option:checked')).getText();
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

export class OfferOrderDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-offerOrder-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-offerOrder'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
