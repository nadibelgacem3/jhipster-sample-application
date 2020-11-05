import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { OfferOrderComponentsPage, OfferOrderDeleteDialog, OfferOrderUpdatePage } from './offer-order.page-object';

const expect = chai.expect;

describe('OfferOrder e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let offerOrderComponentsPage: OfferOrderComponentsPage;
  let offerOrderUpdatePage: OfferOrderUpdatePage;
  let offerOrderDeleteDialog: OfferOrderDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OfferOrders', async () => {
    await navBarPage.goToEntity('offer-order');
    offerOrderComponentsPage = new OfferOrderComponentsPage();
    await browser.wait(ec.visibilityOf(offerOrderComponentsPage.title), 5000);
    expect(await offerOrderComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinecommerceOfferOrder.home.title');
    await browser.wait(ec.or(ec.visibilityOf(offerOrderComponentsPage.entities), ec.visibilityOf(offerOrderComponentsPage.noResult)), 1000);
  });

  it('should load create OfferOrder page', async () => {
    await offerOrderComponentsPage.clickOnCreateButton();
    offerOrderUpdatePage = new OfferOrderUpdatePage();
    expect(await offerOrderUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinecommerceOfferOrder.home.createOrEditLabel');
    await offerOrderUpdatePage.cancel();
  });

  it('should create and save OfferOrders', async () => {
    const nbButtonsBeforeCreate = await offerOrderComponentsPage.countDeleteButtons();

    await offerOrderComponentsPage.clickOnCreateButton();

    await promise.all([
      offerOrderUpdatePage.setQuantityInput('5'),
      offerOrderUpdatePage.setTotalHTInput('5'),
      offerOrderUpdatePage.setTotalTVAInput('5'),
      offerOrderUpdatePage.setTotaTTCInput('5'),
      offerOrderUpdatePage.setDateInput('2000-12-31'),
      offerOrderUpdatePage.statusSelectLastOption(),
      offerOrderUpdatePage.setRequesterOfferIDInput('5'),
      offerOrderUpdatePage.offerSelectLastOption(),
    ]);

    expect(await offerOrderUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await offerOrderUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await offerOrderUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await offerOrderUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');
    expect(await offerOrderUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');
    expect(await offerOrderUpdatePage.getRequesterOfferIDInput()).to.eq('5', 'Expected requesterOfferID value to be equals to 5');

    await offerOrderUpdatePage.save();
    expect(await offerOrderUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await offerOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OfferOrder', async () => {
    const nbButtonsBeforeDelete = await offerOrderComponentsPage.countDeleteButtons();
    await offerOrderComponentsPage.clickOnLastDeleteButton();

    offerOrderDeleteDialog = new OfferOrderDeleteDialog();
    expect(await offerOrderDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinecommerceOfferOrder.delete.question');
    await offerOrderDeleteDialog.clickOnConfirmButton();

    expect(await offerOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
