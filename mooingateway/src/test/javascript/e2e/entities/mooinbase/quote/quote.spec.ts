import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { QuoteComponentsPage, QuoteDeleteDialog, QuoteUpdatePage } from './quote.page-object';

const expect = chai.expect;

describe('Quote e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let quoteComponentsPage: QuoteComponentsPage;
  let quoteUpdatePage: QuoteUpdatePage;
  let quoteDeleteDialog: QuoteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Quotes', async () => {
    await navBarPage.goToEntity('quote');
    quoteComponentsPage = new QuoteComponentsPage();
    await browser.wait(ec.visibilityOf(quoteComponentsPage.title), 5000);
    expect(await quoteComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseQuote.home.title');
    await browser.wait(ec.or(ec.visibilityOf(quoteComponentsPage.entities), ec.visibilityOf(quoteComponentsPage.noResult)), 1000);
  });

  it('should load create Quote page', async () => {
    await quoteComponentsPage.clickOnCreateButton();
    quoteUpdatePage = new QuoteUpdatePage();
    expect(await quoteUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseQuote.home.createOrEditLabel');
    await quoteUpdatePage.cancel();
  });

  it('should create and save Quotes', async () => {
    const nbButtonsBeforeCreate = await quoteComponentsPage.countDeleteButtons();

    await quoteComponentsPage.clickOnCreateButton();

    await promise.all([
      quoteUpdatePage.setNumberInput('number'),
      quoteUpdatePage.setReferenceInput('reference'),
      quoteUpdatePage.statusSelectLastOption(),
      quoteUpdatePage.typeSelectLastOption(),
      quoteUpdatePage.setTotalHTInput('5'),
      quoteUpdatePage.setTotalTVAInput('5'),
      quoteUpdatePage.setTotaTTCInput('5'),
      quoteUpdatePage.setDateInput('2000-12-31'),
      quoteUpdatePage.setDueDateInput('2000-12-31'),
      quoteUpdatePage.setCompanyIDInput('5'),
      quoteUpdatePage.tiersSelectLastOption(),
    ]);

    expect(await quoteUpdatePage.getNumberInput()).to.eq('number', 'Expected Number value to be equals to number');
    expect(await quoteUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');
    expect(await quoteUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await quoteUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await quoteUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');
    expect(await quoteUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');
    expect(await quoteUpdatePage.getDueDateInput()).to.eq('2000-12-31', 'Expected dueDate value to be equals to 2000-12-31');
    const selectedIsConverted = quoteUpdatePage.getIsConvertedInput();
    if (await selectedIsConverted.isSelected()) {
      await quoteUpdatePage.getIsConvertedInput().click();
      expect(await quoteUpdatePage.getIsConvertedInput().isSelected(), 'Expected isConverted not to be selected').to.be.false;
    } else {
      await quoteUpdatePage.getIsConvertedInput().click();
      expect(await quoteUpdatePage.getIsConvertedInput().isSelected(), 'Expected isConverted to be selected').to.be.true;
    }
    expect(await quoteUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');

    await quoteUpdatePage.save();
    expect(await quoteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await quoteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Quote', async () => {
    const nbButtonsBeforeDelete = await quoteComponentsPage.countDeleteButtons();
    await quoteComponentsPage.clickOnLastDeleteButton();

    quoteDeleteDialog = new QuoteDeleteDialog();
    expect(await quoteDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseQuote.delete.question');
    await quoteDeleteDialog.clickOnConfirmButton();

    expect(await quoteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
