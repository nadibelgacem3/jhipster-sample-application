import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { TiersBankCheckComponentsPage, TiersBankCheckDeleteDialog, TiersBankCheckUpdatePage } from './tiers-bank-check.page-object';

const expect = chai.expect;

describe('TiersBankCheck e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tiersBankCheckComponentsPage: TiersBankCheckComponentsPage;
  let tiersBankCheckUpdatePage: TiersBankCheckUpdatePage;
  let tiersBankCheckDeleteDialog: TiersBankCheckDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TiersBankChecks', async () => {
    await navBarPage.goToEntity('tiers-bank-check');
    tiersBankCheckComponentsPage = new TiersBankCheckComponentsPage();
    await browser.wait(ec.visibilityOf(tiersBankCheckComponentsPage.title), 5000);
    expect(await tiersBankCheckComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseTiersBankCheck.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(tiersBankCheckComponentsPage.entities), ec.visibilityOf(tiersBankCheckComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TiersBankCheck page', async () => {
    await tiersBankCheckComponentsPage.clickOnCreateButton();
    tiersBankCheckUpdatePage = new TiersBankCheckUpdatePage();
    expect(await tiersBankCheckUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseTiersBankCheck.home.createOrEditLabel');
    await tiersBankCheckUpdatePage.cancel();
  });

  it('should create and save TiersBankChecks', async () => {
    const nbButtonsBeforeCreate = await tiersBankCheckComponentsPage.countDeleteButtons();

    await tiersBankCheckComponentsPage.clickOnCreateButton();

    await promise.all([
      tiersBankCheckUpdatePage.setNameInput('name'),
      tiersBankCheckUpdatePage.setBankNameInput('bankName'),
      tiersBankCheckUpdatePage.setNumberInput('number'),
      tiersBankCheckUpdatePage.setAmountInput('5'),
      tiersBankCheckUpdatePage.setDueDateInput('2000-12-31'),
      tiersBankCheckUpdatePage.bankCheckTypeSelectLastOption(),
      tiersBankCheckUpdatePage.bankCheckKindSelectLastOption(),
      tiersBankCheckUpdatePage.setSwiftInput('swift'),
      tiersBankCheckUpdatePage.setTypeInput('type'),
      tiersBankCheckUpdatePage.tiersSelectLastOption(),
    ]);

    expect(await tiersBankCheckUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await tiersBankCheckUpdatePage.getBankNameInput()).to.eq('bankName', 'Expected BankName value to be equals to bankName');
    expect(await tiersBankCheckUpdatePage.getNumberInput()).to.eq('number', 'Expected Number value to be equals to number');
    expect(await tiersBankCheckUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await tiersBankCheckUpdatePage.getDueDateInput()).to.eq('2000-12-31', 'Expected dueDate value to be equals to 2000-12-31');
    expect(await tiersBankCheckUpdatePage.getSwiftInput()).to.eq('swift', 'Expected Swift value to be equals to swift');
    expect(await tiersBankCheckUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');

    await tiersBankCheckUpdatePage.save();
    expect(await tiersBankCheckUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tiersBankCheckComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TiersBankCheck', async () => {
    const nbButtonsBeforeDelete = await tiersBankCheckComponentsPage.countDeleteButtons();
    await tiersBankCheckComponentsPage.clickOnLastDeleteButton();

    tiersBankCheckDeleteDialog = new TiersBankCheckDeleteDialog();
    expect(await tiersBankCheckDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseTiersBankCheck.delete.question');
    await tiersBankCheckDeleteDialog.clickOnConfirmButton();

    expect(await tiersBankCheckComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
