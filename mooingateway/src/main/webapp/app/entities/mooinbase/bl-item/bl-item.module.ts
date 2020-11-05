import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { BLItemComponent } from './bl-item.component';
import { BLItemDetailComponent } from './bl-item-detail.component';
import { BLItemUpdateComponent } from './bl-item-update.component';
import { BLItemDeleteDialogComponent } from './bl-item-delete-dialog.component';
import { bLItemRoute } from './bl-item.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(bLItemRoute)],
  declarations: [BLItemComponent, BLItemDetailComponent, BLItemUpdateComponent, BLItemDeleteDialogComponent],
  entryComponents: [BLItemDeleteDialogComponent],
})
export class MooinbaseBLItemModule {}
