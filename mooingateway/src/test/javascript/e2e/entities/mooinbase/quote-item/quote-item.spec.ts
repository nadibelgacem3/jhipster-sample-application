import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { QuoteItemComponentsPage, QuoteItemDeleteDialog, QuoteItemUpdatePage } from './quote-item.page-object';

const expect = chai.expect;

describe('QuoteItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let quoteItemComponentsPage: QuoteItemComponentsPage;
  let quoteItemUpdatePage: QuoteItemUpdatePage;
  let quoteItemDeleteDialog: QuoteItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load QuoteItems', async () => {
    await navBarPage.goToEntity('quote-item');
    quoteItemComponentsPage = new QuoteItemComponentsPage();
    await browser.wait(ec.visibilityOf(quoteItemComponentsPage.title), 5000);
    expect(await quoteItemComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseQuoteItem.home.title');
    await browser.wait(ec.or(ec.visibilityOf(quoteItemComponentsPage.entities), ec.visibilityOf(quoteItemComponentsPage.noResult)), 1000);
  });

  it('should load create QuoteItem page', async () => {
    await quoteItemComponentsPage.clickOnCreateButton();
    quoteItemUpdatePage = new QuoteItemUpdatePage();
    expect(await quoteItemUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseQuoteItem.home.createOrEditLabel');
    await quoteItemUpdatePage.cancel();
  });

  it('should create and save QuoteItems', async () => {
    const nbButtonsBeforeCreate = await quoteItemComponentsPage.countDeleteButtons();

    await quoteItemComponentsPage.clickOnCreateButton();

    await promise.all([
      quoteItemUpdatePage.setQuantityInput('5'),
      quoteItemUpdatePage.setDiscountRateInput('5'),
      quoteItemUpdatePage.setTotalHTInput('5'),
      quoteItemUpdatePage.setTotalTVAInput('5'),
      quoteItemUpdatePage.setTotaTTCInput('5'),
      quoteItemUpdatePage.productSelectLastOption(),
      quoteItemUpdatePage.quoteSelectLastOption(),
    ]);

    expect(await quoteItemUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await quoteItemUpdatePage.getDiscountRateInput()).to.eq('5', 'Expected discountRate value to be equals to 5');
    expect(await quoteItemUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await quoteItemUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await quoteItemUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');

    await quoteItemUpdatePage.save();
    expect(await quoteItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await quoteItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last QuoteItem', async () => {
    const nbButtonsBeforeDelete = await quoteItemComponentsPage.countDeleteButtons();
    await quoteItemComponentsPage.clickOnLastDeleteButton();

    quoteItemDeleteDialog = new QuoteItemDeleteDialog();
    expect(await quoteItemDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseQuoteItem.delete.question');
    await quoteItemDeleteDialog.clickOnConfirmButton();

    expect(await quoteItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
