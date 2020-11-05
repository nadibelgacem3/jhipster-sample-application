import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CompanyBillPaymentComponent } from './company-bill-payment.component';
import { CompanyBillPaymentDetailComponent } from './company-bill-payment-detail.component';
import { CompanyBillPaymentUpdateComponent } from './company-bill-payment-update.component';
import { CompanyBillPaymentDeleteDialogComponent } from './company-bill-payment-delete-dialog.component';
import { companyBillPaymentRoute } from './company-bill-payment.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(companyBillPaymentRoute)],
  declarations: [
    CompanyBillPaymentComponent,
    CompanyBillPaymentDetailComponent,
    CompanyBillPaymentUpdateComponent,
    CompanyBillPaymentDeleteDialogComponent,
  ],
  entryComponents: [CompanyBillPaymentDeleteDialogComponent],
})
export class MooincompaniesCompanyBillPaymentModule {}
