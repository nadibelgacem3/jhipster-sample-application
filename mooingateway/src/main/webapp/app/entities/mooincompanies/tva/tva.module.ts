import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { TVAComponent } from './tva.component';
import { TVADetailComponent } from './tva-detail.component';
import { TVAUpdateComponent } from './tva-update.component';
import { TVADeleteDialogComponent } from './tva-delete-dialog.component';
import { tVARoute } from './tva.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(tVARoute)],
  declarations: [TVAComponent, TVADetailComponent, TVAUpdateComponent, TVADeleteDialogComponent],
  entryComponents: [TVADeleteDialogComponent],
})
export class MooincompaniesTVAModule {}
