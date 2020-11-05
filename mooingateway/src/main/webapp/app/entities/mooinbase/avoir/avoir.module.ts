import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { AvoirComponent } from './avoir.component';
import { AvoirDetailComponent } from './avoir-detail.component';
import { AvoirUpdateComponent } from './avoir-update.component';
import { AvoirDeleteDialogComponent } from './avoir-delete-dialog.component';
import { avoirRoute } from './avoir.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(avoirRoute)],
  declarations: [AvoirComponent, AvoirDetailComponent, AvoirUpdateComponent, AvoirDeleteDialogComponent],
  entryComponents: [AvoirDeleteDialogComponent],
})
export class MooinbaseAvoirModule {}
