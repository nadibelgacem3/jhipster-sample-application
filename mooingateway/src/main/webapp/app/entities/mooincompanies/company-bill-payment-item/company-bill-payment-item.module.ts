import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CompanyBillPaymentItemComponent } from './company-bill-payment-item.component';
import { CompanyBillPaymentItemDetailComponent } from './company-bill-payment-item-detail.component';
import { CompanyBillPaymentItemUpdateComponent } from './company-bill-payment-item-update.component';
import { CompanyBillPaymentItemDeleteDialogComponent } from './company-bill-payment-item-delete-dialog.component';
import { companyBillPaymentItemRoute } from './company-bill-payment-item.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(companyBillPaymentItemRoute)],
  declarations: [
    CompanyBillPaymentItemComponent,
    CompanyBillPaymentItemDetailComponent,
    CompanyBillPaymentItemUpdateComponent,
    CompanyBillPaymentItemDeleteDialogComponent,
  ],
  entryComponents: [CompanyBillPaymentItemDeleteDialogComponent],
})
export class MooincompaniesCompanyBillPaymentItemModule {}
