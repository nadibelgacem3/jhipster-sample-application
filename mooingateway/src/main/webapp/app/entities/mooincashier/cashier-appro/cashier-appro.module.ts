import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CashierApproComponent } from './cashier-appro.component';
import { CashierApproDetailComponent } from './cashier-appro-detail.component';
import { CashierApproUpdateComponent } from './cashier-appro-update.component';
import { CashierApproDeleteDialogComponent } from './cashier-appro-delete-dialog.component';
import { cashierApproRoute } from './cashier-appro.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(cashierApproRoute)],
  declarations: [CashierApproComponent, CashierApproDetailComponent, CashierApproUpdateComponent, CashierApproDeleteDialogComponent],
  entryComponents: [CashierApproDeleteDialogComponent],
})
export class MooincashierCashierApproModule {}
