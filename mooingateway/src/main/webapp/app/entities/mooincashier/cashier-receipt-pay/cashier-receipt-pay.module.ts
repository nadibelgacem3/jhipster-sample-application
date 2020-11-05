import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CashierReceiptPayComponent } from './cashier-receipt-pay.component';
import { CashierReceiptPayDetailComponent } from './cashier-receipt-pay-detail.component';
import { CashierReceiptPayUpdateComponent } from './cashier-receipt-pay-update.component';
import { CashierReceiptPayDeleteDialogComponent } from './cashier-receipt-pay-delete-dialog.component';
import { cashierReceiptPayRoute } from './cashier-receipt-pay.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(cashierReceiptPayRoute)],
  declarations: [
    CashierReceiptPayComponent,
    CashierReceiptPayDetailComponent,
    CashierReceiptPayUpdateComponent,
    CashierReceiptPayDeleteDialogComponent,
  ],
  entryComponents: [CashierReceiptPayDeleteDialogComponent],
})
export class MooincashierCashierReceiptPayModule {}
