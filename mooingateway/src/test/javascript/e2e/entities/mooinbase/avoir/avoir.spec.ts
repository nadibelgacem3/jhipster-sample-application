import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { AvoirComponentsPage, AvoirDeleteDialog, AvoirUpdatePage } from './avoir.page-object';

const expect = chai.expect;

describe('Avoir e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let avoirComponentsPage: AvoirComponentsPage;
  let avoirUpdatePage: AvoirUpdatePage;
  let avoirDeleteDialog: AvoirDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Avoirs', async () => {
    await navBarPage.goToEntity('avoir');
    avoirComponentsPage = new AvoirComponentsPage();
    await browser.wait(ec.visibilityOf(avoirComponentsPage.title), 5000);
    expect(await avoirComponentsPage.getTitle()).to.eq('mooingatewayApp.mooinbaseAvoir.home.title');
    await browser.wait(ec.or(ec.visibilityOf(avoirComponentsPage.entities), ec.visibilityOf(avoirComponentsPage.noResult)), 1000);
  });

  it('should load create Avoir page', async () => {
    await avoirComponentsPage.clickOnCreateButton();
    avoirUpdatePage = new AvoirUpdatePage();
    expect(await avoirUpdatePage.getPageTitle()).to.eq('mooingatewayApp.mooinbaseAvoir.home.createOrEditLabel');
    await avoirUpdatePage.cancel();
  });

  it('should create and save Avoirs', async () => {
    const nbButtonsBeforeCreate = await avoirComponentsPage.countDeleteButtons();

    await avoirComponentsPage.clickOnCreateButton();

    await promise.all([
      avoirUpdatePage.setNumberInput('number'),
      avoirUpdatePage.setReferenceInput('reference'),
      avoirUpdatePage.statusSelectLastOption(),
      avoirUpdatePage.typeSelectLastOption(),
      avoirUpdatePage.setTotalHTInput('5'),
      avoirUpdatePage.setTotalTVAInput('5'),
      avoirUpdatePage.setTotaTTCInput('5'),
      avoirUpdatePage.setDateInput('2000-12-31'),
      avoirUpdatePage.setDueDateInput('2000-12-31'),
      avoirUpdatePage.setCompanyIDInput('5'),
      avoirUpdatePage.tiersSelectLastOption(),
    ]);

    expect(await avoirUpdatePage.getNumberInput()).to.eq('number', 'Expected Number value to be equals to number');
    expect(await avoirUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');
    expect(await avoirUpdatePage.getTotalHTInput()).to.eq('5', 'Expected totalHT value to be equals to 5');
    expect(await avoirUpdatePage.getTotalTVAInput()).to.eq('5', 'Expected totalTVA value to be equals to 5');
    expect(await avoirUpdatePage.getTotaTTCInput()).to.eq('5', 'Expected totaTTC value to be equals to 5');
    expect(await avoirUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');
    expect(await avoirUpdatePage.getDueDateInput()).to.eq('2000-12-31', 'Expected dueDate value to be equals to 2000-12-31');
    const selectedIsConverted = avoirUpdatePage.getIsConvertedInput();
    if (await selectedIsConverted.isSelected()) {
      await avoirUpdatePage.getIsConvertedInput().click();
      expect(await avoirUpdatePage.getIsConvertedInput().isSelected(), 'Expected isConverted not to be selected').to.be.false;
    } else {
      await avoirUpdatePage.getIsConvertedInput().click();
      expect(await avoirUpdatePage.getIsConvertedInput().isSelected(), 'Expected isConverted to be selected').to.be.true;
    }
    expect(await avoirUpdatePage.getCompanyIDInput()).to.eq('5', 'Expected companyID value to be equals to 5');

    await avoirUpdatePage.save();
    expect(await avoirUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await avoirComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Avoir', async () => {
    const nbButtonsBeforeDelete = await avoirComponentsPage.countDeleteButtons();
    await avoirComponentsPage.clickOnLastDeleteButton();

    avoirDeleteDialog = new AvoirDeleteDialog();
    expect(await avoirDeleteDialog.getDialogTitle()).to.eq('mooingatewayApp.mooinbaseAvoir.delete.question');
    await avoirDeleteDialog.clickOnConfirmButton();

    expect(await avoirComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
