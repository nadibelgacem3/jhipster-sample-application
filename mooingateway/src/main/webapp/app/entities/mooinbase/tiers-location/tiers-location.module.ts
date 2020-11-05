import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { TiersLocationComponent } from './tiers-location.component';
import { TiersLocationDetailComponent } from './tiers-location-detail.component';
import { TiersLocationUpdateComponent } from './tiers-location-update.component';
import { TiersLocationDeleteDialogComponent } from './tiers-location-delete-dialog.component';
import { tiersLocationRoute } from './tiers-location.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(tiersLocationRoute)],
  declarations: [TiersLocationComponent, TiersLocationDetailComponent, TiersLocationUpdateComponent, TiersLocationDeleteDialogComponent],
  entryComponents: [TiersLocationDeleteDialogComponent],
})
export class MooinbaseTiersLocationModule {}
