import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { InvoicePaymentHistoryComponent } from './invoice-payment-history.component';
import { InvoicePaymentHistoryDetailComponent } from './invoice-payment-history-detail.component';
import { InvoicePaymentHistoryUpdateComponent } from './invoice-payment-history-update.component';
import { InvoicePaymentHistoryDeleteDialogComponent } from './invoice-payment-history-delete-dialog.component';
import { invoicePaymentHistoryRoute } from './invoice-payment-history.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(invoicePaymentHistoryRoute)],
  declarations: [
    InvoicePaymentHistoryComponent,
    InvoicePaymentHistoryDetailComponent,
    InvoicePaymentHistoryUpdateComponent,
    InvoicePaymentHistoryDeleteDialogComponent,
  ],
  entryComponents: [InvoicePaymentHistoryDeleteDialogComponent],
})
export class MooinbaseInvoicePaymentHistoryModule {}
