import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CashierReceiptComponent } from './cashier-receipt.component';
import { CashierReceiptDetailComponent } from './cashier-receipt-detail.component';
import { CashierReceiptUpdateComponent } from './cashier-receipt-update.component';
import { CashierReceiptDeleteDialogComponent } from './cashier-receipt-delete-dialog.component';
import { cashierReceiptRoute } from './cashier-receipt.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(cashierReceiptRoute)],
  declarations: [
    CashierReceiptComponent,
    CashierReceiptDetailComponent,
    CashierReceiptUpdateComponent,
    CashierReceiptDeleteDialogComponent,
  ],
  entryComponents: [CashierReceiptDeleteDialogComponent],
})
export class MooincashierCashierReceiptModule {}
