import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CompanyBankAccountComponent } from './company-bank-account.component';
import { CompanyBankAccountDetailComponent } from './company-bank-account-detail.component';
import { CompanyBankAccountUpdateComponent } from './company-bank-account-update.component';
import { CompanyBankAccountDeleteDialogComponent } from './company-bank-account-delete-dialog.component';
import { companyBankAccountRoute } from './company-bank-account.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(companyBankAccountRoute)],
  declarations: [
    CompanyBankAccountComponent,
    CompanyBankAccountDetailComponent,
    CompanyBankAccountUpdateComponent,
    CompanyBankAccountDeleteDialogComponent,
  ],
  entryComponents: [CompanyBankAccountDeleteDialogComponent],
})
export class MooincompaniesCompanyBankAccountModule {}
