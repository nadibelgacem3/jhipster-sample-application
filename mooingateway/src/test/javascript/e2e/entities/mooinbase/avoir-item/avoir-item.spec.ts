import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { AvoirItemComponentsPage, AvoirItemDeleteDialog, AvoirItemUpdatePage } from './avoir-item.page-object';

const expect = chai.expect;

describe('AvoirItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let avoirItemComponentsPage: AvoirItemComponentsPage;
  let avoirItemUpdatePage: AvoirItemUpdatePage;
  let avoirItemDeleteDialog: AvoirItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AvoirItems', async () => {
    await navBarPage.goToEntity('avoir-item');
    avoirItemComponentsPage = new AvoirItemComponentsPage();
    await browser.wait(ec.visibilityOf(avoirItemComponentsPage.title), 5000);
    expect(await avoirItemComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseAvoirItem.home.title');
    await browser.wait(ec.or(ec.visibilityOf(avoirItemComponentsPage.entities), ec.visibilityOf(avoirItemComponentsPage.noResult)), 1000);
  });

  it('should load create AvoirItem page', async () => {
    await avoirItemComponentsPage.clickOnCreateButton();
    avoirItemUpdatePage = new AvoirItemUpdatePage();
    expect(await avoirItemUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseAvoirItem.home.createOrEditLabel');
    await avoirItemUpdatePage.cancel();
  });

  it('should create and save AvoirItems', async () => {
    const nbButtonsBeforeCreate = await avoirItemComponentsPage.countDeleteButtons();

    await avoirItemComponentsPage.clickOnCreateButton();

    await promise.all([
      avoirItemUpdatePage.setQuantityInput('5'),
      avoirItemUpdatePage.setDiscountRateInput('5'),
      avoirItemUpdatePage.setTotalHTInput('5'),
      avoirItemUpdatePage.setTotalTVAInput('5'),
      avoirItemUpdatePage.setTotaTTCInput('5'),
      avoirItemUpdatePage.productSelectLastOption(),
      avoirItemUpdatePage.avoirSelectLastOption(),
    ]);

    expect(await avoirItemUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await avoirItemUpdatePage.getDiscountRateInput()).to.eq('5', 'Expected discountRate value to be equals to 5');
    expect(await avoirItemUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await avoirItemUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await avoirItemUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');

    await avoirItemUpdatePage.save();
    expect(await avoirItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await avoirItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AvoirItem', async () => {
    const nbButtonsBeforeDelete = await avoirItemComponentsPage.countDeleteButtons();
    await avoirItemComponentsPage.clickOnLastDeleteButton();

    avoirItemDeleteDialog = new AvoirItemDeleteDialog();
    expect(await avoirItemDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseAvoirItem.delete.question');
    await avoirItemDeleteDialog.clickOnConfirmButton();

    expect(await avoirItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
