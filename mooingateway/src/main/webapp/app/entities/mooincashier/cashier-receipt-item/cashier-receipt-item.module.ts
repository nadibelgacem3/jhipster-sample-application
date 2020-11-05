import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CashierReceiptItemComponent } from './cashier-receipt-item.component';
import { CashierReceiptItemDetailComponent } from './cashier-receipt-item-detail.component';
import { CashierReceiptItemUpdateComponent } from './cashier-receipt-item-update.component';
import { CashierReceiptItemDeleteDialogComponent } from './cashier-receipt-item-delete-dialog.component';
import { cashierReceiptItemRoute } from './cashier-receipt-item.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(cashierReceiptItemRoute)],
  declarations: [
    CashierReceiptItemComponent,
    CashierReceiptItemDetailComponent,
    CashierReceiptItemUpdateComponent,
    CashierReceiptItemDeleteDialogComponent,
  ],
  entryComponents: [CashierReceiptItemDeleteDialogComponent],
})
export class MooincashierCashierReceiptItemModule {}
