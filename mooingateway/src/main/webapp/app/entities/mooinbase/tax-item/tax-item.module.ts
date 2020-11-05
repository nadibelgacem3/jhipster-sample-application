import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { TaxItemComponent } from './tax-item.component';
import { TaxItemDetailComponent } from './tax-item-detail.component';
import { TaxItemUpdateComponent } from './tax-item-update.component';
import { TaxItemDeleteDialogComponent } from './tax-item-delete-dialog.component';
import { taxItemRoute } from './tax-item.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(taxItemRoute)],
  declarations: [TaxItemComponent, TaxItemDetailComponent, TaxItemUpdateComponent, TaxItemDeleteDialogComponent],
  entryComponents: [TaxItemDeleteDialogComponent],
})
export class MooinbaseTaxItemModule {}
