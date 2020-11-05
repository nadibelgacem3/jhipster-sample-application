import { element, by, ElementFinder } from 'protractor';

export class OfferComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-offer div table .btn-danger'));
  title = element.all(by.css('jhi-offer div h2#page-heading span')).first();
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

export class OfferUpdatePage {
  pageTitle = element(by.id('jhi-offer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  imageInput = element(by.id('file_image'));
  dateBeginInput = element(by.id('field_dateBegin'));
  dateEndInput = element(by.id('field_dateEnd'));
  durationInput = element(by.id('field_duration'));
  statusSelect = element(by.id('field_status'));
  productIdInput = element(by.id('field_productId'));
  saleUnitPriceBeforeDiscountInput = element(by.id('field_saleUnitPriceBeforeDiscount'));
  discountRateInput = element(by.id('field_discountRate'));
  saleUnitPriceAfterDiscountInput = element(by.id('field_saleUnitPriceAfterDiscount'));
  withTVAInput = element(by.id('field_withTVA'));
  withTaxInput = element(by.id('field_withTax'));
  isActivatedInput = element(by.id('field_isActivated'));
  companyIDInput = element(by.id('field_companyID'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setImageInput(image: string): Promise<void> {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput(): Promise<string> {
    return await this.imageInput.getAttribute('value');
  }

  async setDateBeginInput(dateBegin: string): Promise<void> {
    await this.dateBeginInput.sendKeys(dateBegin);
  }

  async getDateBeginInput(): Promise<string> {
    return await this.dateBeginInput.getAttribute('value');
  }

  async setDateEndInput(dateEnd: string): Promise<void> {
    await this.dateEndInput.sendKeys(dateEnd);
  }

  async getDateEndInput(): Promise<string> {
    return await this.dateEndInput.getAttribute('value');
  }

  async setDurationInput(duration: string): Promise<void> {
    await this.durationInput.sendKeys(duration);
  }

  async getDurationInput(): Promise<string> {
    return await this.durationInput.getAttribute('value');
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

  async setProductIdInput(productId: string): Promise<void> {
    await this.productIdInput.sendKeys(productId);
  }

  async getProductIdInput(): Promise<string> {
    return await this.productIdInput.getAttribute('value');
  }

  async setSaleUnitPriceBeforeDiscountInput(saleUnitPriceBeforeDiscount: string): Promise<void> {
    await this.saleUnitPriceBeforeDiscountInput.sendKeys(saleUnitPriceBeforeDiscount);
  }

  async getSaleUnitPriceBeforeDiscountInput(): Promise<string> {
    return await this.saleUnitPriceBeforeDiscountInput.getAttribute('value');
  }

  async setDiscountRateInput(discountRate: string): Promise<void> {
    await this.discountRateInput.sendKeys(discountRate);
  }

  async getDiscountRateInput(): Promise<string> {
    return await this.discountRateInput.getAttribute('value');
  }

  async setSaleUnitPriceAfterDiscountInput(saleUnitPriceAfterDiscount: string): Promise<void> {
    await this.saleUnitPriceAfterDiscountInput.sendKeys(saleUnitPriceAfterDiscount);
  }

  async getSaleUnitPriceAfterDiscountInput(): Promise<string> {
    return await this.saleUnitPriceAfterDiscountInput.getAttribute('value');
  }

  getWithTVAInput(): ElementFinder {
    return this.withTVAInput;
  }

  getWithTaxInput(): ElementFinder {
    return this.withTaxInput;
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

export class OfferDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-offer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-offer'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
