import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { TVAItemComponent } from './tva-item.component';
import { TVAItemDetailComponent } from './tva-item-detail.component';
import { TVAItemUpdateComponent } from './tva-item-update.component';
import { TVAItemDeleteDialogComponent } from './tva-item-delete-dialog.component';
import { tVAItemRoute } from './tva-item.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(tVAItemRoute)],
  declarations: [TVAItemComponent, TVAItemDetailComponent, TVAItemUpdateComponent, TVAItemDeleteDialogComponent],
  entryComponents: [TVAItemDeleteDialogComponent],
})
export class MooinbaseTVAItemModule {}
