import { element, by, ElementFinder } from 'protractor';

export class CompanyComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-company div table .btn-danger'));
  title = element.all(by.css('jhi-company div h2#page-heading span')).first();
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

export class CompanyUpdatePage {
  pageTitle = element(by.id('jhi-company-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  logoInput = element(by.id('file_logo'));
  phone1Input = element(by.id('field_phone1'));
  phone2Input = element(by.id('field_phone2'));
  faxInput = element(by.id('field_fax'));
  email1Input = element(by.id('field_email1'));
  email2Input = element(by.id('field_email2'));
  descriptionInput = element(by.id('field_description'));
  businessTypeSelect = element(by.id('field_businessType'));
  currencyUnitInput = element(by.id('field_currencyUnit'));
  currencyQuotientInput = element(by.id('field_currencyQuotient'));
  commercialRegisterInput = element(by.id('field_commercialRegister'));
  isActivatedInput = element(by.id('field_isActivated'));
  themeColorInput = element(by.id('field_themeColor'));
  facebookInput = element(by.id('field_facebook'));
  linkedinInput = element(by.id('field_linkedin'));
  twitterInput = element(by.id('field_twitter'));
  instagramInput = element(by.id('field_instagram'));

  companyBankAccountSelect = element(by.id('field_companyBankAccount'));
  companyLocationSelect = element(by.id('field_companyLocation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setLogoInput(logo: string): Promise<void> {
    await this.logoInput.sendKeys(logo);
  }

  async getLogoInput(): Promise<string> {
    return await this.logoInput.getAttribute('value');
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

  async setFaxInput(fax: string): Promise<void> {
    await this.faxInput.sendKeys(fax);
  }

  async getFaxInput(): Promise<string> {
    return await this.faxInput.getAttribute('value');
  }

  async setEmail1Input(email1: string): Promise<void> {
    await this.email1Input.sendKeys(email1);
  }

  async getEmail1Input(): Promise<string> {
    return await this.email1Input.getAttribute('value');
  }

  async setEmail2Input(email2: string): Promise<void> {
    await this.email2Input.sendKeys(email2);
  }

  async getEmail2Input(): Promise<string> {
    return await this.email2Input.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setBusinessTypeSelect(businessType: string): Promise<void> {
    await this.businessTypeSelect.sendKeys(businessType);
  }

  async getBusinessTypeSelect(): Promise<string> {
    return await this.businessTypeSelect.element(by.css('option:checked')).getText();
  }

  async businessTypeSelectLastOption(): Promise<void> {
    await this.businessTypeSelect.all(by.tagName('option')).last().click();
  }

  async setCurrencyUnitInput(currencyUnit: string): Promise<void> {
    await this.currencyUnitInput.sendKeys(currencyUnit);
  }

  async getCurrencyUnitInput(): Promise<string> {
    return await this.currencyUnitInput.getAttribute('value');
  }

  async setCurrencyQuotientInput(currencyQuotient: string): Promise<void> {
    await this.currencyQuotientInput.sendKeys(currencyQuotient);
  }

  async getCurrencyQuotientInput(): Promise<string> {
    return await this.currencyQuotientInput.getAttribute('value');
  }

  async setCommercialRegisterInput(commercialRegister: string): Promise<void> {
    await this.commercialRegisterInput.sendKeys(commercialRegister);
  }

  async getCommercialRegisterInput(): Promise<string> {
    return await this.commercialRegisterInput.getAttribute('value');
  }

  getIsActivatedInput(): ElementFinder {
    return this.isActivatedInput;
  }

  async setThemeColorInput(themeColor: string): Promise<void> {
    await this.themeColorInput.sendKeys(themeColor);
  }

  async getThemeColorInput(): Promise<string> {
    return await this.themeColorInput.getAttribute('value');
  }

  async setFacebookInput(facebook: string): Promise<void> {
    await this.facebookInput.sendKeys(facebook);
  }

  async getFacebookInput(): Promise<string> {
    return await this.facebookInput.getAttribute('value');
  }

  async setLinkedinInput(linkedin: string): Promise<void> {
    await this.linkedinInput.sendKeys(linkedin);
  }

  async getLinkedinInput(): Promise<string> {
    return await this.linkedinInput.getAttribute('value');
  }

  async setTwitterInput(twitter: string): Promise<void> {
    await this.twitterInput.sendKeys(twitter);
  }

  async getTwitterInput(): Promise<string> {
    return await this.twitterInput.getAttribute('value');
  }

  async setInstagramInput(instagram: string): Promise<void> {
    await this.instagramInput.sendKeys(instagram);
  }

  async getInstagramInput(): Promise<string> {
    return await this.instagramInput.getAttribute('value');
  }

  async companyBankAccountSelectLastOption(): Promise<void> {
    await this.companyBankAccountSelect.all(by.tagName('option')).last().click();
  }

  async companyBankAccountSelectOption(option: string): Promise<void> {
    await this.companyBankAccountSelect.sendKeys(option);
  }

  getCompanyBankAccountSelect(): ElementFinder {
    return this.companyBankAccountSelect;
  }

  async getCompanyBankAccountSelectedOption(): Promise<string> {
    return await this.companyBankAccountSelect.element(by.css('option:checked')).getText();
  }

  async companyLocationSelectLastOption(): Promise<void> {
    await this.companyLocationSelect.all(by.tagName('option')).last().click();
  }

  async companyLocationSelectOption(option: string): Promise<void> {
    await this.companyLocationSelect.sendKeys(option);
  }

  getCompanyLocationSelect(): ElementFinder {
    return this.companyLocationSelect;
  }

  async getCompanyLocationSelectedOption(): Promise<string> {
    return await this.companyLocationSelect.element(by.css('option:checked')).getText();
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

export class CompanyDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-company-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-company'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
