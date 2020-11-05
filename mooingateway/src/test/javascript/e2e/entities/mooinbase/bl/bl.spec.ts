import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { BLComponentsPage, BLDeleteDialog, BLUpdatePage } from './bl.page-object';

const expect = chai.expect;

describe('BL e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bLComponentsPage: BLComponentsPage;
  let bLUpdatePage: BLUpdatePage;
  let bLDeleteDialog: BLDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BLS', async () => {
    await navBarPage.goToEntity('bl');
    bLComponentsPage = new BLComponentsPage();
    await browser.wait(ec.visibilityOf(bLComponentsPage.title), 5000);
    expect(await bLComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseBL.home.title');
    await browser.wait(ec.or(ec.visibilityOf(bLComponentsPage.entities), ec.visibilityOf(bLComponentsPage.noResult)), 1000);
  });

  it('should load create BL page', async () => {
    await bLComponentsPage.clickOnCreateButton();
    bLUpdatePage = new BLUpdatePage();
    expect(await bLUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseBL.home.createOrEditLabel');
    await bLUpdatePage.cancel();
  });

  it('should create and save BLS', async () => {
    const nbButtonsBeforeCreate = await bLComponentsPage.countDeleteButtons();

    await bLComponentsPage.clickOnCreateButton();

    await promise.all([
      bLUpdatePage.setNumberInput('number'),
      bLUpdatePage.setReferenceInput('reference'),
      bLUpdatePage.statusSelectLastOption(),
      bLUpdatePage.typeSelectLastOption(),
      bLUpdatePage.setTotalHTInput('5'),
      bLUpdatePage.setTotalTVAInput('5'),
      bLUpdatePage.setTotaTTCInput('5'),
      bLUpdatePage.setDateInput('2000-12-31'),
      bLUpdatePage.setDueDateInput('2000-12-31'),
      bLUpdatePage.setCompanyIDInput('5'),
      bLUpdatePage.tiersSelectLastOption(),
    ]);

    expect(await bLUpdatePage.getNumberInput()).to.eq('number', 'Expected Number value to be equals to number');
    expect(await bLUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');
    expect(await bLUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await bLUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await bLUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');
    expect(await bLUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');
    expect(await bLUpdatePage.getDueDateInput()).to.eq('2000-12-31', 'Expected dueDate value to be equals to 2000-12-31');
    const selectedIsConverted = bLUpdatePage.getIsConvertedInput();
    if (await selectedIsConverted.isSelected()) {
      await bLUpdatePage.getIsConvertedInput().click();
      expect(await bLUpdatePage.getIsConvertedInput().isSelected(), 'Expected isConverted not to be selected').to.be.false;
    } else {
      await bLUpdatePage.getIsConvertedInput().click();
      expect(await bLUpdatePage.getIsConvertedInput().isSelected(), 'Expected isConverted to be selected').to.be.true;
    }
    expect(await bLUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');

    await bLUpdatePage.save();
    expect(await bLUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bLComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BL', async () => {
    const nbButtonsBeforeDelete = await bLComponentsPage.countDeleteButtons();
    await bLComponentsPage.clickOnLastDeleteButton();

    bLDeleteDialog = new BLDeleteDialog();
    expect(await bLDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseBL.delete.question');
    await bLDeleteDialog.clickOnConfirmButton();

    expect(await bLComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
