import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { BLComponent } from './bl.component';
import { BLDetailComponent } from './bl-detail.component';
import { BLUpdateComponent } from './bl-update.component';
import { BLDeleteDialogComponent } from './bl-delete-dialog.component';
import { bLRoute } from './bl.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(bLRoute)],
  declarations: [BLComponent, BLDetailComponent, BLUpdateComponent, BLDeleteDialogComponent],
  entryComponents: [BLDeleteDialogComponent],
})
export class MooinbaseBLModule {}
