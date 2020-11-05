import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CashierProductComponent } from './cashier-product.component';
import { CashierProductDetailComponent } from './cashier-product-detail.component';
import { CashierProductUpdateComponent } from './cashier-product-update.component';
import { CashierProductDeleteDialogComponent } from './cashier-product-delete-dialog.component';
import { cashierProductRoute } from './cashier-product.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(cashierProductRoute)],
  declarations: [
    CashierProductComponent,
    CashierProductDetailComponent,
    CashierProductUpdateComponent,
    CashierProductDeleteDialogComponent,
  ],
  entryComponents: [CashierProductDeleteDialogComponent],
})
export class MooincashierCashierProductModule {}
