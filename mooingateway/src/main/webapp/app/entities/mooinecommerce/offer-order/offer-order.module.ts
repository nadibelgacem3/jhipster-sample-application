import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { OfferOrderComponent } from './offer-order.component';
import { OfferOrderDetailComponent } from './offer-order-detail.component';
import { OfferOrderUpdateComponent } from './offer-order-update.component';
import { OfferOrderDeleteDialogComponent } from './offer-order-delete-dialog.component';
import { offerOrderRoute } from './offer-order.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(offerOrderRoute)],
  declarations: [OfferOrderComponent, OfferOrderDetailComponent, OfferOrderUpdateComponent, OfferOrderDeleteDialogComponent],
  entryComponents: [OfferOrderDeleteDialogComponent],
})
export class MooinecommerceOfferOrderModule {}
