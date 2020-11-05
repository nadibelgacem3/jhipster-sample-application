import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  CompanyBankAccountComponentsPage,
  CompanyBankAccountDeleteDialog,
  CompanyBankAccountUpdatePage,
} from './company-bank-account.page-object';

const expect = chai.expect;

describe('CompanyBankAccount e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyBankAccountComponentsPage: CompanyBankAccountComponentsPage;
  let companyBankAccountUpdatePage: CompanyBankAccountUpdatePage;
  let companyBankAccountDeleteDialog: CompanyBankAccountDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CompanyBankAccounts', async () => {
    await navBarPage.goToEntity('company-bank-account');
    companyBankAccountComponentsPage = new CompanyBankAccountComponentsPage();
    await browser.wait(ec.visibilityOf(companyBankAccountComponentsPage.title), 5000);
    expect(await companyBankAccountComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyBankAccount.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(companyBankAccountComponentsPage.entities), ec.visibilityOf(companyBankAccountComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CompanyBankAccount page', async () => {
    await companyBankAccountComponentsPage.clickOnCreateButton();
    companyBankAccountUpdatePage = new CompanyBankAccountUpdatePage();
    expect(await companyBankAccountUpdatePage.getPageTitle()).to.eq(
      'mooingatewayApp.mooincompaniesCompanyBankAccount.home.createOrEditLabel'
    );
    await companyBankAccountUpdatePage.cancel();
  });

  it('should create and save CompanyBankAccounts', async () => {
    const nbButtonsBeforeCreate = await companyBankAccountComponentsPage.countDeleteButtons();

    await companyBankAccountComponentsPage.clickOnCreateButton();

    await promise.all([
      companyBankAccountUpdatePage.setReferenceInput('reference'),
      companyBankAccountUpdatePage.setBankNameInput('bankName'),
      companyBankAccountUpdatePage.setBankAccountNumberInput('bankAccountNumber'),
      companyBankAccountUpdatePage.setIbanInput('iban'),
      companyBankAccountUpdatePage.setSwiftInput('swift'),
      companyBankAccountUpdatePage.setTypeInput('type'),
    ]);

    expect(await companyBankAccountUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');
    expect(await companyBankAccountUpdatePage.getBankNameInput()).to.eq('bankName', 'Expected BankName value to be equals to bankName');
    expect(await companyBankAccountUpdatePage.getBankAccountNumberInput()).to.eq(
      'bankAccountNumber',
      'Expected BankAccountNumber value to be equals to bankAccountNumber'
    );
    expect(await companyBankAccountUpdatePage.getIbanInput()).to.eq('iban', 'Expected Iban value to be equals to iban');
    expect(await companyBankAccountUpdatePage.getSwiftInput()).to.eq('swift', 'Expected Swift value to be equals to swift');
    expect(await companyBankAccountUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');

    await companyBankAccountUpdatePage.save();
    expect(await companyBankAccountUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await companyBankAccountComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CompanyBankAccount', async () => {
    const nbButtonsBeforeDelete = await companyBankAccountComponentsPage.countDeleteButtons();
    await companyBankAccountComponentsPage.clickOnLastDeleteButton();

    companyBankAccountDeleteDialog = new CompanyBankAccountDeleteDialog();
    expect(await companyBankAccountDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyBankAccount.delete.question');
    await companyBankAccountDeleteDialog.clickOnConfirmButton();

    expect(await companyBankAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
