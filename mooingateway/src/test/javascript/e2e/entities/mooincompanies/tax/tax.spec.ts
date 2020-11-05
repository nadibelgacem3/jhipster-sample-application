import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { TaxComponentsPage, TaxDeleteDialog, TaxUpdatePage } from './tax.page-object';

const expect = chai.expect;

describe('Tax e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let taxComponentsPage: TaxComponentsPage;
  let taxUpdatePage: TaxUpdatePage;
  let taxDeleteDialog: TaxDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Taxes', async () => {
    await navBarPage.goToEntity('tax');
    taxComponentsPage = new TaxComponentsPage();
    await browser.wait(ec.visibilityOf(taxComponentsPage.title), 5000);
    expect(await taxComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesTax.home.title');
    await browser.wait(ec.or(ec.visibilityOf(taxComponentsPage.entities), ec.visibilityOf(taxComponentsPage.noResult)), 1000);
  });

  it('should load create Tax page', async () => {
    await taxComponentsPage.clickOnCreateButton();
    taxUpdatePage = new TaxUpdatePage();
    expect(await taxUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincompaniesTax.home.createOrEditLabel');
    await taxUpdatePage.cancel();
  });

  it('should create and save Taxes', async () => {
    const nbButtonsBeforeCreate = await taxComponentsPage.countDeleteButtons();

    await taxComponentsPage.clickOnCreateButton();

    await promise.all([taxUpdatePage.setNameInput('name'), taxUpdatePage.setValueInput('5'), taxUpdatePage.companySelectLastOption()]);

    expect(await taxUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    const selectedIsValued = taxUpdatePage.getIsValuedInput();
    if (await selectedIsValued.isSelected()) {
      await taxUpdatePage.getIsValuedInput().click();
      expect(await taxUpdatePage.getIsValuedInput().isSelected(), 'Expected isValued not to be selected').to.be.false;
    } else {
      await taxUpdatePage.getIsValuedInput().click();
      expect(await taxUpdatePage.getIsValuedInput().isSelected(), 'Expected isValued to be selected').to.be.true;
    }
    const selectedIsPercentage = taxUpdatePage.getIsPercentageInput();
    if (await selectedIsPercentage.isSelected()) {
      await taxUpdatePage.getIsPercentageInput().click();
      expect(await taxUpdatePage.getIsPercentageInput().isSelected(), 'Expected isPercentage not to be selected').to.be.false;
    } else {
      await taxUpdatePage.getIsPercentageInput().click();
      expect(await taxUpdatePage.getIsPercentageInput().isSelected(), 'Expected isPercentage to be selected').to.be.true;
    }
    expect(await taxUpdatePage.getValueInput()).to.eq('5', 'Expected value value to be equals to 5');

    await taxUpdatePage.save();
    expect(await taxUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await taxComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Tax', async () => {
    const nbButtonsBeforeDelete = await taxComponentsPage.countDeleteButtons();
    await taxComponentsPage.clickOnLastDeleteButton();

    taxDeleteDialog = new TaxDeleteDialog();
    expect(await taxDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesTax.delete.question');
    await taxDeleteDialog.clickOnConfirmButton();

    expect(await taxComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
