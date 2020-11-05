import { element, by, ElementFinder } from 'protractor';

export class EmployeeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employee div table .btn-danger'));
  title = element.all(by.css('jhi-employee div h2#page-heading span')).first();
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

export class EmployeeUpdatePage {
  pageTitle = element(by.id('jhi-employee-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  firstNameInput = element(by.id('field_firstName'));
  lastNameInput = element(by.id('field_lastName'));
  jobTitleInput = element(by.id('field_jobTitle'));
  genderSelect = element(by.id('field_gender'));
  dateOfborthInput = element(by.id('field_dateOfborth'));
  dateOfRecruitmentInput = element(by.id('field_dateOfRecruitment'));
  imageInput = element(by.id('file_image'));
  emailInput = element(by.id('field_email'));
  phoneNumberInput = element(by.id('field_phoneNumber'));
  salaryInput = element(by.id('field_salary'));
  commissionPctInput = element(by.id('field_commissionPct'));
  ratesInput = element(by.id('field_rates'));

  employeeLocationSelect = element(by.id('field_employeeLocation'));
  companySelect = element(by.id('field_company'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async setJobTitleInput(jobTitle: string): Promise<void> {
    await this.jobTitleInput.sendKeys(jobTitle);
  }

  async getJobTitleInput(): Promise<string> {
    return await this.jobTitleInput.getAttribute('value');
  }

  async setGenderSelect(gender: string): Promise<void> {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect(): Promise<string> {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption(): Promise<void> {
    await this.genderSelect.all(by.tagName('option')).last().click();
  }

  async setDateOfborthInput(dateOfborth: string): Promise<void> {
    await this.dateOfborthInput.sendKeys(dateOfborth);
  }

  async getDateOfborthInput(): Promise<string> {
    return await this.dateOfborthInput.getAttribute('value');
  }

  async setDateOfRecruitmentInput(dateOfRecruitment: string): Promise<void> {
    await this.dateOfRecruitmentInput.sendKeys(dateOfRecruitment);
  }

  async getDateOfRecruitmentInput(): Promise<string> {
    return await this.dateOfRecruitmentInput.getAttribute('value');
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

  async setPhoneNumberInput(phoneNumber: string): Promise<void> {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput(): Promise<string> {
    return await this.phoneNumberInput.getAttribute('value');
  }

  async setSalaryInput(salary: string): Promise<void> {
    await this.salaryInput.sendKeys(salary);
  }

  async getSalaryInput(): Promise<string> {
    return await this.salaryInput.getAttribute('value');
  }

  async setCommissionPctInput(commissionPct: string): Promise<void> {
    await this.commissionPctInput.sendKeys(commissionPct);
  }

  async getCommissionPctInput(): Promise<string> {
    return await this.commissionPctInput.getAttribute('value');
  }

  async setRatesInput(rates: string): Promise<void> {
    await this.ratesInput.sendKeys(rates);
  }

  async getRatesInput(): Promise<string> {
    return await this.ratesInput.getAttribute('value');
  }

  async employeeLocationSelectLastOption(): Promise<void> {
    await this.employeeLocationSelect.all(by.tagName('option')).last().click();
  }

  async employeeLocationSelectOption(option: string): Promise<void> {
    await this.employeeLocationSelect.sendKeys(option);
  }

  getEmployeeLocationSelect(): ElementFinder {
    return this.employeeLocationSelect;
  }

  async getEmployeeLocationSelectedOption(): Promise<string> {
    return await this.employeeLocationSelect.element(by.css('option:checked')).getText();
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

export class EmployeeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-employee-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-employee'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
