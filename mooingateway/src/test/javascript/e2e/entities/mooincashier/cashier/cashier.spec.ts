import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { CashierComponentsPage, CashierDeleteDialog, CashierUpdatePage } from './cashier.page-object';

const expect = chai.expect;

describe('Cashier e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cashierComponentsPage: CashierComponentsPage;
  let cashierUpdatePage: CashierUpdatePage;
  let cashierDeleteDialog: CashierDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Cashiers', async () => {
    await navBarPage.goToEntity('cashier');
    cashierComponentsPage = new CashierComponentsPage();
    await browser.wait(ec.visibilityOf(cashierComponentsPage.title), 5000);
    expect(await cashierComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincashierCashier.home.title');
    await browser.wait(ec.or(ec.visibilityOf(cashierComponentsPage.entities), ec.visibilityOf(cashierComponentsPage.noResult)), 1000);
  });

  it('should load create Cashier page', async () => {
    await cashierComponentsPage.clickOnCreateButton();
    cashierUpdatePage = new CashierUpdatePage();
    expect(await cashierUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincashierCashier.home.createOrEditLabel');
    await cashierUpdatePage.cancel();
  });

  it('should create and save Cashiers', async () => {
    const nbButtonsBeforeCreate = await cashierComponentsPage.countDeleteButtons();

    await cashierComponentsPage.clickOnCreateButton();

    await promise.all([
      cashierUpdatePage.setNameInput('name'),
      cashierUpdatePage.setThemeColorInput('themeColor'),
      cashierUpdatePage.setCompanyIDInput('5'),
      cashierUpdatePage.cashierLocationSelectLastOption(),
    ]);

    expect(await cashierUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    const selectedWithTicket = cashierUpdatePage.getWithTicketInput();
    if (await selectedWithTicket.isSelected()) {
      await cashierUpdatePage.getWithTicketInput().click();
      expect(await cashierUpdatePage.getWithTicketInput().isSelected(), 'Expected withTicket not to be selected').to.be.false;
    } else {
      await cashierUpdatePage.getWithTicketInput().click();
      expect(await cashierUpdatePage.getWithTicketInput().isSelected(), 'Expected withTicket to be selected').to.be.true;
    }
    const selectedWithTVA = cashierUpdatePage.getWithTVAInput();
    if (await selectedWithTVA.isSelected()) {
      await cashierUpdatePage.getWithTVAInput().click();
      expect(await cashierUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA not to be selected').to.be.false;
    } else {
      await cashierUpdatePage.getWithTVAInput().click();
      expect(await cashierUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA to be selected').to.be.true;
    }
    const selectedWithTax = cashierUpdatePage.getWithTaxInput();
    if (await selectedWithTax.isSelected()) {
      await cashierUpdatePage.getWithTaxInput().click();
      expect(await cashierUpdatePage.getWithTaxInput().isSelected(), 'Expected withTax not to be selected').to.be.false;
    } else {
      await cashierUpdatePage.getWithTaxInput().click();
      expect(await cashierUpdatePage.getWithTaxInput().isSelected(), 'Expected withTax to be selected').to.be.true;
    }
    const selectedWithAppro = cashierUpdatePage.getWithApproInput();
    if (await selectedWithAppro.isSelected()) {
      await cashierUpdatePage.getWithApproInput().click();
      expect(await cashierUpdatePage.getWithApproInput().isSelected(), 'Expected withAppro not to be selected').to.be.false;
    } else {
      await cashierUpdatePage.getWithApproInput().click();
      expect(await cashierUpdatePage.getWithApproInput().isSelected(), 'Expected withAppro to be selected').to.be.true;
    }
    expect(await cashierUpdatePage.getThemeColorInput()).to.eq('themeColor', 'Expected ThemeColor value to be equals to themeColor');
    const selectedIsActivated = cashierUpdatePage.getIsActivatedInput();
    if (await selectedIsActivated.isSelected()) {
      await cashierUpdatePage.getIsActivatedInput().click();
      expect(await cashierUpdatePage.getIsActivatedInput().isSelected(), 'Expected isActivated not to be selected').to.be.false;
    } else {
      await cashierUpdatePage.getIsActivatedInput().click();
      expect(await cashierUpdatePage.getIsActivatedInput().isSelected(), 'Expected isActivated to be selected').to.be.true;
    }
    expect(await cashierUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');

    await cashierUpdatePage.save();
    expect(await cashierUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cashierComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Cashier', async () => {
    const nbButtonsBeforeDelete = await cashierComponentsPage.countDeleteButtons();
    await cashierComponentsPage.clickOnLastDeleteButton();

    cashierDeleteDialog = new CashierDeleteDialog();
    expect(await cashierDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincashierCashier.delete.question');
    await cashierDeleteDialog.clickOnConfirmButton();

    expect(await cashierComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
