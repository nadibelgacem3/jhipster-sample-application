import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { ProductMarkComponent } from './product-mark.component';
import { ProductMarkDetailComponent } from './product-mark-detail.component';
import { ProductMarkUpdateComponent } from './product-mark-update.component';
import { ProductMarkDeleteDialogComponent } from './product-mark-delete-dialog.component';
import { productMarkRoute } from './product-mark.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(productMarkRoute)],
  declarations: [ProductMarkComponent, ProductMarkDetailComponent, ProductMarkUpdateComponent, ProductMarkDeleteDialogComponent],
  entryComponents: [ProductMarkDeleteDialogComponent],
})
export class MooinbaseProductMarkModule {}
