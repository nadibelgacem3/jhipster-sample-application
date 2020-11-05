import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { CashierApproComponentsPage, CashierApproDeleteDialog, CashierApproUpdatePage } from './cashier-appro.page-object';

const expect = chai.expect;

describe('CashierAppro e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cashierApproComponentsPage: CashierApproComponentsPage;
  let cashierApproUpdatePage: CashierApproUpdatePage;
  let cashierApproDeleteDialog: CashierApproDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CashierAppros', async () => {
    await navBarPage.goToEntity('cashier-appro');
    cashierApproComponentsPage = new CashierApproComponentsPage();
    await browser.wait(ec.visibilityOf(cashierApproComponentsPage.title), 5000);
    expect(await cashierApproComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincashierCashierAppro.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(cashierApproComponentsPage.entities), ec.visibilityOf(cashierApproComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CashierAppro page', async () => {
    await cashierApproComponentsPage.clickOnCreateButton();
    cashierApproUpdatePage = new CashierApproUpdatePage();
    expect(await cashierApproUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincashierCashierAppro.home.createOrEditLabel');
    await cashierApproUpdatePage.cancel();
  });

  it('should create and save CashierAppros', async () => {
    const nbButtonsBeforeCreate = await cashierApproComponentsPage.countDeleteButtons();

    await cashierApproComponentsPage.clickOnCreateButton();

    await promise.all([
      cashierApproUpdatePage.setNumberInput('number'),
      cashierApproUpdatePage.statusSelectLastOption(),
      cashierApproUpdatePage.setTotalHTInput('5'),
      cashierApproUpdatePage.setTotalTVAInput('5'),
      cashierApproUpdatePage.setTotaTTCInput('5'),
      cashierApproUpdatePage.setDateInput('2000-12-31'),
      cashierApproUpdatePage.cashierSelectLastOption(),
    ]);

    expect(await cashierApproUpdatePage.getNumberInput()).to.eq('number', 'Expected Number value to be equals to number');
    expect(await cashierApproUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await cashierApproUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await cashierApproUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');
    expect(await cashierApproUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');
    const selectedIsConverted = cashierApproUpdatePage.getIsConvertedInput();
    if (await selectedIsConverted.isSelected()) {
      await cashierApproUpdatePage.getIsConvertedInput().click();
      expect(await cashierApproUpdatePage.getIsConvertedInput().isSelected(), 'Expected isConverted not to be selected').to.be.false;
    } else {
      await cashierApproUpdatePage.getIsConvertedInput().click();
      expect(await cashierApproUpdatePage.getIsConvertedInput().isSelected(), 'Expected isConverted to be selected').to.be.true;
    }
    const selectedWithTVA = cashierApproUpdatePage.getWithTVAInput();
    if (await selectedWithTVA.isSelected()) {
      await cashierApproUpdatePage.getWithTVAInput().click();
      expect(await cashierApproUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA not to be selected').to.be.false;
    } else {
      await cashierApproUpdatePage.getWithTVAInput().click();
      expect(await cashierApproUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA to be selected').to.be.true;
    }
    const selectedWithTax = cashierApproUpdatePage.getWithTaxInput();
    if (await selectedWithTax.isSelected()) {
      await cashierApproUpdatePage.getWithTaxInput().click();
      expect(await cashierApproUpdatePage.getWithTaxInput().isSelected(), 'Expected withTax not to be selected').to.be.false;
    } else {
      await cashierApproUpdatePage.getWithTaxInput().click();
      expect(await cashierApproUpdatePage.getWithTaxInput().isSelected(), 'Expected withTax to be selected').to.be.true;
    }

    await cashierApproUpdatePage.save();
    expect(await cashierApproUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cashierApproComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last CashierAppro', async () => {
    const nbButtonsBeforeDelete = await cashierApproComponentsPage.countDeleteButtons();
    await cashierApproComponentsPage.clickOnLastDeleteButton();

    cashierApproDeleteDialog = new CashierApproDeleteDialog();
    expect(await cashierApproDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincashierCashierAppro.delete.question');
    await cashierApproDeleteDialog.clickOnConfirmButton();

    expect(await cashierApproComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
