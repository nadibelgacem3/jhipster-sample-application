import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { CompanyModuleComponentsPage, CompanyModuleDeleteDialog, CompanyModuleUpdatePage } from './company-module.page-object';

const expect = chai.expect;

describe('CompanyModule e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyModuleComponentsPage: CompanyModuleComponentsPage;
  let companyModuleUpdatePage: CompanyModuleUpdatePage;
  let companyModuleDeleteDialog: CompanyModuleDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CompanyModules', async () => {
    await navBarPage.goToEntity('company-module');
    companyModuleComponentsPage = new CompanyModuleComponentsPage();
    await browser.wait(ec.visibilityOf(companyModuleComponentsPage.title), 5000);
    expect(await companyModuleComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyModule.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(companyModuleComponentsPage.entities), ec.visibilityOf(companyModuleComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CompanyModule page', async () => {
    await companyModuleComponentsPage.clickOnCreateButton();
    companyModuleUpdatePage = new CompanyModuleUpdatePage();
    expect(await companyModuleUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyModule.home.createOrEditLabel');
    await companyModuleUpdatePage.cancel();
  });

  it('should create and save CompanyModules', async () => {
    const nbButtonsBeforeCreate = await companyModuleComponentsPage.countDeleteButtons();

    await companyModuleComponentsPage.clickOnCreateButton();

    await promise.all([
      companyModuleUpdatePage.setNameInput('name'),
      companyModuleUpdatePage.setActivatedAtInput('2000-12-31'),
      companyModuleUpdatePage.setActivatedUntilInput('2000-12-31'),
      companyModuleUpdatePage.setPriceInput('5'),
      companyModuleUpdatePage.companySelectLastOption(),
    ]);

    expect(await companyModuleUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await companyModuleUpdatePage.getActivatedAtInput()).to.eq(
      '2000-12-31',
      'Expected activatedAt value to be equals to 2000-12-31'
    );
    expect(await companyModuleUpdatePage.getActivatedUntilInput()).to.eq(
      '2000-12-31',
      'Expected activatedUntil value to be equals to 2000-12-31'
    );
    const selectedIsActivated = companyModuleUpdatePage.getIsActivatedInput();
    if (await selectedIsActivated.isSelected()) {
      await companyModuleUpdatePage.getIsActivatedInput().click();
      expect(await companyModuleUpdatePage.getIsActivatedInput().isSelected(), 'Expected isActivated not to be selected').to.be.false;
    } else {
      await companyModuleUpdatePage.getIsActivatedInput().click();
      expect(await companyModuleUpdatePage.getIsActivatedInput().isSelected(), 'Expected isActivated to be selected').to.be.true;
    }
    expect(await companyModuleUpdatePage.getPriceInput()).to.eq('5', 'Expected price value to be equals to 5');

    await companyModuleUpdatePage.save();
    expect(await companyModuleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await companyModuleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last CompanyModule', async () => {
    const nbButtonsBeforeDelete = await companyModuleComponentsPage.countDeleteButtons();
    await companyModuleComponentsPage.clickOnLastDeleteButton();

    companyModuleDeleteDialog = new CompanyModuleDeleteDialog();
    expect(await companyModuleDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesCompanyModule.delete.question');
    await companyModuleDeleteDialog.clickOnConfirmButton();

    expect(await companyModuleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
