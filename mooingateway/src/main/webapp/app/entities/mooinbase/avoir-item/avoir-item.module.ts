import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { AvoirItemComponent } from './avoir-item.component';
import { AvoirItemDetailComponent } from './avoir-item-detail.component';
import { AvoirItemUpdateComponent } from './avoir-item-update.component';
import { AvoirItemDeleteDialogComponent } from './avoir-item-delete-dialog.component';
import { avoirItemRoute } from './avoir-item.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(avoirItemRoute)],
  declarations: [AvoirItemComponent, AvoirItemDetailComponent, AvoirItemUpdateComponent, AvoirItemDeleteDialogComponent],
  entryComponents: [AvoirItemDeleteDialogComponent],
})
export class MooinbaseAvoirItemModule {}
