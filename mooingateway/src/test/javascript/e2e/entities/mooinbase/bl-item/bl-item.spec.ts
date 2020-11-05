import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { BLItemComponentsPage, BLItemDeleteDialog, BLItemUpdatePage } from './bl-item.page-object';

const expect = chai.expect;

describe('BLItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bLItemComponentsPage: BLItemComponentsPage;
  let bLItemUpdatePage: BLItemUpdatePage;
  let bLItemDeleteDialog: BLItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BLItems', async () => {
    await navBarPage.goToEntity('bl-item');
    bLItemComponentsPage = new BLItemComponentsPage();
    await browser.wait(ec.visibilityOf(bLItemComponentsPage.title), 5000);
    expect(await bLItemComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseBLItem.home.title');
    await browser.wait(ec.or(ec.visibilityOf(bLItemComponentsPage.entities), ec.visibilityOf(bLItemComponentsPage.noResult)), 1000);
  });

  it('should load create BLItem page', async () => {
    await bLItemComponentsPage.clickOnCreateButton();
    bLItemUpdatePage = new BLItemUpdatePage();
    expect(await bLItemUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseBLItem.home.createOrEditLabel');
    await bLItemUpdatePage.cancel();
  });

  it('should create and save BLItems', async () => {
    const nbButtonsBeforeCreate = await bLItemComponentsPage.countDeleteButtons();

    await bLItemComponentsPage.clickOnCreateButton();

    await promise.all([
      bLItemUpdatePage.setQuantityInput('5'),
      bLItemUpdatePage.setDiscountRateInput('5'),
      bLItemUpdatePage.setTotalHTInput('5'),
      bLItemUpdatePage.setTotalTVAInput('5'),
      bLItemUpdatePage.setTotaTTCInput('5'),
      bLItemUpdatePage.productSelectLastOption(),
      bLItemUpdatePage.blSelectLastOption(),
    ]);

    expect(await bLItemUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await bLItemUpdatePage.getDiscountRateInput()).to.eq('5', 'Expected discountRate value to be equals to 5');
    expect(await bLItemUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await bLItemUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await bLItemUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');

    await bLItemUpdatePage.save();
    expect(await bLItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bLItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BLItem', async () => {
    const nbButtonsBeforeDelete = await bLItemComponentsPage.countDeleteButtons();
    await bLItemComponentsPage.clickOnLastDeleteButton();

    bLItemDeleteDialog = new BLItemDeleteDialog();
    expect(await bLItemDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseBLItem.delete.question');
    await bLItemDeleteDialog.clickOnConfirmButton();

    expect(await bLItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
