import { element, by, ElementFinder } from 'protractor';

export class EmployeeLocationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employee-location div table .btn-danger'));
  title = element.all(by.css('jhi-employee-location div h2#page-heading span')).first();
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

export class EmployeeLocationUpdatePage {
  pageTitle = element(by.id('jhi-employee-location-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  localNumberInput = element(by.id('field_localNumber'));
  streetAddressInput = element(by.id('field_streetAddress'));
  postalCodeInput = element(by.id('field_postalCode'));
  cityInput = element(by.id('field_city'));
  stateProvinceInput = element(by.id('field_stateProvince'));
  countryNameInput = element(by.id('field_countryName'));
  flagInput = element(by.id('file_flag'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLocalNumberInput(localNumber: string): Promise<void> {
    await this.localNumberInput.sendKeys(localNumber);
  }

  async getLocalNumberInput(): Promise<string> {
    return await this.localNumberInput.getAttribute('value');
  }

  async setStreetAddressInput(streetAddress: string): Promise<void> {
    await this.streetAddressInput.sendKeys(streetAddress);
  }

  async getStreetAddressInput(): Promise<string> {
    return await this.streetAddressInput.getAttribute('value');
  }

  async setPostalCodeInput(postalCode: string): Promise<void> {
    await this.postalCodeInput.sendKeys(postalCode);
  }

  async getPostalCodeInput(): Promise<string> {
    return await this.postalCodeInput.getAttribute('value');
  }

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setStateProvinceInput(stateProvince: string): Promise<void> {
    await this.stateProvinceInput.sendKeys(stateProvince);
  }

  async getStateProvinceInput(): Promise<string> {
    return await this.stateProvinceInput.getAttribute('value');
  }

  async setCountryNameInput(countryName: string): Promise<void> {
    await this.countryNameInput.sendKeys(countryName);
  }

  async getCountryNameInput(): Promise<string> {
    return await this.countryNameInput.getAttribute('value');
  }

  async setFlagInput(flag: string): Promise<void> {
    await this.flagInput.sendKeys(flag);
  }

  async getFlagInput(): Promise<string> {
    return await this.flagInput.getAttribute('value');
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

export class EmployeeLocationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-employeeLocation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-employeeLocation'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
