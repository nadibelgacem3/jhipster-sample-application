import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { TVAItemComponentsPage, TVAItemDeleteDialog, TVAItemUpdatePage } from './tva-item.page-object';

const expect = chai.expect;

describe('TVAItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tVAItemComponentsPage: TVAItemComponentsPage;
  let tVAItemUpdatePage: TVAItemUpdatePage;
  let tVAItemDeleteDialog: TVAItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TVAItems', async () => {
    await navBarPage.goToEntity('tva-item');
    tVAItemComponentsPage = new TVAItemComponentsPage();
    await browser.wait(ec.visibilityOf(tVAItemComponentsPage.title), 5000);
    expect(await tVAItemComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseTVaItem.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tVAItemComponentsPage.entities), ec.visibilityOf(tVAItemComponentsPage.noResult)), 1000);
  });

  it('should load create TVAItem page', async () => {
    await tVAItemComponentsPage.clickOnCreateButton();
    tVAItemUpdatePage = new TVAItemUpdatePage();
    expect(await tVAItemUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseTVaItem.home.createOrEditLabel');
    await tVAItemUpdatePage.cancel();
  });

  it('should create and save TVAItems', async () => {
    const nbButtonsBeforeCreate = await tVAItemComponentsPage.countDeleteButtons();

    await tVAItemComponentsPage.clickOnCreateButton();

    await promise.all([
      tVAItemUpdatePage.setNameInput('name'),
      tVAItemUpdatePage.setPercentageValueInput('5'),
      tVAItemUpdatePage.setCompanyIDInput('5'),
      tVAItemUpdatePage.setTvaIDInput('5'),
      tVAItemUpdatePage.productSelectLastOption(),
    ]);

    expect(await tVAItemUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await tVAItemUpdatePage.getPercentageValueInput()).to.eq('5', 'Expected percentageValue value to be equals to 5');
    expect(await tVAItemUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');
    expect(await tVAItemUpdatePage.getTvaIDInput()).to.eq('5', 'Expected tvaID value to be equals to 5');

    await tVAItemUpdatePage.save();
    expect(await tVAItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tVAItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TVAItem', async () => {
    const nbButtonsBeforeDelete = await tVAItemComponentsPage.countDeleteButtons();
    await tVAItemComponentsPage.clickOnLastDeleteButton();

    tVAItemDeleteDialog = new TVAItemDeleteDialog();
    expect(await tVAItemDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseTVaItem.delete.question');
    await tVAItemDeleteDialog.clickOnConfirmButton();

    expect(await tVAItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
