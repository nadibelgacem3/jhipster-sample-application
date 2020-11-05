import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { CashierApproItemComponentsPage, CashierApproItemDeleteDialog, CashierApproItemUpdatePage } from './cashier-appro-item.page-object';

const expect = chai.expect;

describe('CashierApproItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cashierApproItemComponentsPage: CashierApproItemComponentsPage;
  let cashierApproItemUpdatePage: CashierApproItemUpdatePage;
  let cashierApproItemDeleteDialog: CashierApproItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CashierApproItems', async () => {
    await navBarPage.goToEntity('cashier-appro-item');
    cashierApproItemComponentsPage = new CashierApproItemComponentsPage();
    await browser.wait(ec.visibilityOf(cashierApproItemComponentsPage.title), 5000);
    expect(await cashierApproItemComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincashierCashierApproItem.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(cashierApproItemComponentsPage.entities), ec.visibilityOf(cashierApproItemComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CashierApproItem page', async () => {
    await cashierApproItemComponentsPage.clickOnCreateButton();
    cashierApproItemUpdatePage = new CashierApproItemUpdatePage();
    expect(await cashierApproItemUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincashierCashierApproItem.home.createOrEditLabel');
    await cashierApproItemUpdatePage.cancel();
  });

  it('should create and save CashierApproItems', async () => {
    const nbButtonsBeforeCreate = await cashierApproItemComponentsPage.countDeleteButtons();

    await cashierApproItemComponentsPage.clickOnCreateButton();

    await promise.all([
      cashierApproItemUpdatePage.setQuantityInput('5'),
      cashierApproItemUpdatePage.setDiscountRateInput('5'),
      cashierApproItemUpdatePage.setTotalHTInput('5'),
      cashierApproItemUpdatePage.setTotalTVAInput('5'),
      cashierApproItemUpdatePage.setTotaTTCInput('5'),
      cashierApproItemUpdatePage.cashierProductSelectLastOption(),
      cashierApproItemUpdatePage.cashierApproSelectLastOption(),
    ]);

    expect(await cashierApproItemUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await cashierApproItemUpdatePage.getDiscountRateInput()).to.eq('5', 'Expected discountRate value to be equals to 5');
    expect(await cashierApproItemUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await cashierApproItemUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await cashierApproItemUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');

    await cashierApproItemUpdatePage.save();
    expect(await cashierApproItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cashierApproItemComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CashierApproItem', async () => {
    const nbButtonsBeforeDelete = await cashierApproItemComponentsPage.countDeleteButtons();
    await cashierApproItemComponentsPage.clickOnLastDeleteButton();

    cashierApproItemDeleteDialog = new CashierApproItemDeleteDialog();
    expect(await cashierApproItemDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincashierCashierApproItem.delete.question');
    await cashierApproItemDeleteDialog.clickOnConfirmButton();

    expect(await cashierApproItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
