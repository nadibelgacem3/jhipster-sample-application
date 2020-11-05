import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CashierLocationComponent } from './cashier-location.component';
import { CashierLocationDetailComponent } from './cashier-location-detail.component';
import { CashierLocationUpdateComponent } from './cashier-location-update.component';
import { CashierLocationDeleteDialogComponent } from './cashier-location-delete-dialog.component';
import { cashierLocationRoute } from './cashier-location.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(cashierLocationRoute)],
  declarations: [
    CashierLocationComponent,
    CashierLocationDetailComponent,
    CashierLocationUpdateComponent,
    CashierLocationDeleteDialogComponent,
  ],
  entryComponents: [CashierLocationDeleteDialogComponent],
})
export class MooincashierCashierLocationModule {}
