import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CashierComponent } from './cashier.component';
import { CashierDetailComponent } from './cashier-detail.component';
import { CashierUpdateComponent } from './cashier-update.component';
import { CashierDeleteDialogComponent } from './cashier-delete-dialog.component';
import { cashierRoute } from './cashier.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(cashierRoute)],
  declarations: [CashierComponent, CashierDetailComponent, CashierUpdateComponent, CashierDeleteDialogComponent],
  entryComponents: [CashierDeleteDialogComponent],
})
export class MooincashierCashierModule {}
