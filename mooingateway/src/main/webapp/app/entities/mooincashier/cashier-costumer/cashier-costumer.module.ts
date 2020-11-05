import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CashierCostumerComponent } from './cashier-costumer.component';
import { CashierCostumerDetailComponent } from './cashier-costumer-detail.component';
import { CashierCostumerUpdateComponent } from './cashier-costumer-update.component';
import { CashierCostumerDeleteDialogComponent } from './cashier-costumer-delete-dialog.component';
import { cashierCostumerRoute } from './cashier-costumer.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(cashierCostumerRoute)],
  declarations: [
    CashierCostumerComponent,
    CashierCostumerDetailComponent,
    CashierCostumerUpdateComponent,
    CashierCostumerDeleteDialogComponent,
  ],
  entryComponents: [CashierCostumerDeleteDialogComponent],
})
export class MooincashierCashierCostumerModule {}
