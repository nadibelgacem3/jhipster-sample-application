import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CashierApproItemComponent } from './cashier-appro-item.component';
import { CashierApproItemDetailComponent } from './cashier-appro-item-detail.component';
import { CashierApproItemUpdateComponent } from './cashier-appro-item-update.component';
import { CashierApproItemDeleteDialogComponent } from './cashier-appro-item-delete-dialog.component';
import { cashierApproItemRoute } from './cashier-appro-item.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(cashierApproItemRoute)],
  declarations: [
    CashierApproItemComponent,
    CashierApproItemDetailComponent,
    CashierApproItemUpdateComponent,
    CashierApproItemDeleteDialogComponent,
  ],
  entryComponents: [CashierApproItemDeleteDialogComponent],
})
export class MooincashierCashierApproItemModule {}
