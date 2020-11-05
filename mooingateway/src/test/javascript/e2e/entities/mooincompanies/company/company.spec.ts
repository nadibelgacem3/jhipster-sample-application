import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { CompanyComponentsPage, CompanyDeleteDialog, CompanyUpdatePage } from './company.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Company e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyComponentsPage: CompanyComponentsPage;
  let companyUpdatePage: CompanyUpdatePage;
  let companyDeleteDialog: CompanyDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Companies', async () => {
    await navBarPage.goToEntity('company');
    companyComponentsPage = new CompanyComponentsPage();
    await browser.wait(ec.visibilityOf(companyComponentsPage.title), 5000);
    expect(await companyComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesCompany.home.title');
    await browser.wait(ec.or(ec.visibilityOf(companyComponentsPage.entities), ec.visibilityOf(companyComponentsPage.noResult)), 1000);
  });

  it('should load create Company page', async () => {
    await companyComponentsPage.clickOnCreateButton();
    companyUpdatePage = new CompanyUpdatePage();
    expect(await companyUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincompaniesCompany.home.createOrEditLabel');
    await companyUpdatePage.cancel();
  });

  it('should create and save Companies', async () => {
    const nbButtonsBeforeCreate = await companyComponentsPage.countDeleteButtons();

    await companyComponentsPage.clickOnCreateButton();

    await promise.all([
      companyUpdatePage.setNameInput('name'),
      companyUpdatePage.setLogoInput(absolutePath),
      companyUpdatePage.setPhone1Input('phone1'),
      companyUpdatePage.setPhone2Input('phone2'),
      companyUpdatePage.setFaxInput('fax'),
      companyUpdatePage.setEmail1Input('LE@*.pQ'),
      companyUpdatePage.setEmail2Input('wP^R@YAn&#34;.fEi'),
      companyUpdatePage.setDescriptionInput('description'),
      companyUpdatePage.businessTypeSelectLastOption(),
      companyUpdatePage.setCurrencyUnitInput('currencyUnit'),
      companyUpdatePage.setCurrencyQuotientInput('5'),
      companyUpdatePage.setCommercialRegisterInput('commercialRegister'),
      companyUpdatePage.setThemeColorInput('themeColor'),
      companyUpdatePage.setFacebookInput('facebook'),
      companyUpdatePage.setLinkedinInput('linkedin'),
      companyUpdatePage.setTwitterInput('twitter'),
      companyUpdatePage.setInstagramInput('instagram'),
      companyUpdatePage.companyBankAccountSelectLastOption(),
      companyUpdatePage.companyLocationSelectLastOption(),
    ]);

    expect(await companyUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await companyUpdatePage.getLogoInput()).to.endsWith(fileNameToUpload, 'Expected Logo value to be end with ' + fileNameToUpload);
    expect(await companyUpdatePage.getPhone1Input()).to.eq('phone1', 'Expected Phone1 value to be equals to phone1');
    expect(await companyUpdatePage.getPhone2Input()).to.eq('phone2', 'Expected Phone2 value to be equals to phone2');
    expect(await companyUpdatePage.getFaxInput()).to.eq('fax', 'Expected Fax value to be equals to fax');
    expect(await companyUpdatePage.getEmail1Input()).to.eq('LE@*.pQ', 'Expected Email1 value to be equals to LE@*.pQ');
    expect(await companyUpdatePage.getEmail2Input()).to.eq('wP^R@YAn&#34;.fEi', 'Expected Email2 value to be equals to wP^R@YAn&#34;.fEi');
    expect(await companyUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await companyUpdatePage.getCurrencyUnitInput()).to.eq(
      'currencyUnit',
      'Expected CurrencyUnit value to be equals to currencyUnit'
    );
    expect(await companyUpdatePage.getCurrencyQuotientInput()).to.eq('5', 'Expected currencyQuotient value to be equals to 5');
    expect(await companyUpdatePage.getCommercialRegisterInput()).to.eq(
      'commercialRegister',
      'Expected CommercialRegister value to be equals to commercialRegister'
    );
    const selectedIsActivated = companyUpdatePage.getIsActivatedInput();
    if (await selectedIsActivated.isSelected()) {
      await companyUpdatePage.getIsActivatedInput().click();
      expect(await companyUpdatePage.getIsActivatedInput().isSelected(), 'Expected isActivated not to be selected').to.be.false;
    } else {
      await companyUpdatePage.getIsActivatedInput().click();
      expect(await companyUpdatePage.getIsActivatedInput().isSelected(), 'Expected isActivated to be selected').to.be.true;
    }
    expect(await companyUpdatePage.getThemeColorInput()).to.eq('themeColor', 'Expected ThemeColor value to be equals to themeColor');
    expect(await companyUpdatePage.getFacebookInput()).to.eq('facebook', 'Expected Facebook value to be equals to facebook');
    expect(await companyUpdatePage.getLinkedinInput()).to.eq('linkedin', 'Expected Linkedin value to be equals to linkedin');
    expect(await companyUpdatePage.getTwitterInput()).to.eq('twitter', 'Expected Twitter value to be equals to twitter');
    expect(await companyUpdatePage.getInstagramInput()).to.eq('instagram', 'Expected Instagram value to be equals to instagram');

    await companyUpdatePage.save();
    expect(await companyUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await companyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Company', async () => {
    const nbButtonsBeforeDelete = await companyComponentsPage.countDeleteButtons();
    await companyComponentsPage.clickOnLastDeleteButton();

    companyDeleteDialog = new CompanyDeleteDialog();
    expect(await companyDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesCompany.delete.question');
    await companyDeleteDialog.clickOnConfirmButton();

    expect(await companyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
