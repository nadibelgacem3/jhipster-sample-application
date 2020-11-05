import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { TransactionCompComponentsPage, TransactionCompDeleteDialog, TransactionCompUpdatePage } from './transaction-comp.page-object';

const expect = chai.expect;

describe('TransactionComp e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let transactionCompComponentsPage: TransactionCompComponentsPage;
  let transactionCompUpdatePage: TransactionCompUpdatePage;
  let transactionCompDeleteDialog: TransactionCompDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TransactionComps', async () => {
    await navBarPage.goToEntity('transaction-comp');
    transactionCompComponentsPage = new TransactionCompComponentsPage();
    await browser.wait(ec.visibilityOf(transactionCompComponentsPage.title), 5000);
    expect(await transactionCompComponentsPage.getTitle()).to.eq('mooingatewayApp.mooincompaniesTransactionComp.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(transactionCompComponentsPage.entities), ec.visibilityOf(transactionCompComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TransactionComp page', async () => {
    await transactionCompComponentsPage.clickOnCreateButton();
    transactionCompUpdatePage = new TransactionCompUpdatePage();
    expect(await transactionCompUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooincompaniesTransactionComp.home.createOrEditLabel');
    await transactionCompUpdatePage.cancel();
  });

  it('should create and save TransactionComps', async () => {
    const nbButtonsBeforeCreate = await transactionCompComponentsPage.countDeleteButtons();

    await transactionCompComponentsPage.clickOnCreateButton();

    await promise.all([
      transactionCompUpdatePage.setNumberInput('number'),
      transactionCompUpdatePage.setDetailsInput('details'),
      transactionCompUpdatePage.typeSelectLastOption(),
      transactionCompUpdatePage.paymentMethodSelectLastOption(),
      transactionCompUpdatePage.setTotalHTInput('5'),
      transactionCompUpdatePage.setTotalTVAInput('5'),
      transactionCompUpdatePage.setTotaTTCInput('5'),
      transactionCompUpdatePage.setDateInput('2000-12-31'),
      transactionCompUpdatePage.companySelectLastOption(),
    ]);

    expect(await transactionCompUpdatePage.getNumberInput()).to.eq('number', 'Expected Number value to be equals to number');
    expect(await transactionCompUpdatePage.getDetailsInput()).to.eq('details', 'Expected Details value to be equals to details');
    const selectedWithTVA = transactionCompUpdatePage.getWithTVAInput();
    if (await selectedWithTVA.isSelected()) {
      await transactionCompUpdatePage.getWithTVAInput().click();
      expect(await transactionCompUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA not to be selected').to.be.false;
    } else {
      await transactionCompUpdatePage.getWithTVAInput().click();
      expect(await transactionCompUpdatePage.getWithTVAInput().isSelected(), 'Expected withTVA to be selected').to.be.true;
    }
    expect(await transactionCompUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await transactionCompUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await transactionCompUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');
    expect(await transactionCompUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');

    await transactionCompUpdatePage.save();
    expect(await transactionCompUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await transactionCompComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TransactionComp', async () => {
    const nbButtonsBeforeDelete = await transactionCompComponentsPage.countDeleteButtons();
    await transactionCompComponentsPage.clickOnLastDeleteButton();

    transactionCompDeleteDialog = new TransactionCompDeleteDialog();
    expect(await transactionCompDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooincompaniesTransactionComp.delete.question');
    await transactionCompDeleteDialog.clickOnConfirmButton();

    expect(await transactionCompComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
