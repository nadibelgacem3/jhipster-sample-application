import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { TiersComponent } from './tiers.component';
import { TiersDetailComponent } from './tiers-detail.component';
import { TiersUpdateComponent } from './tiers-update.component';
import { TiersDeleteDialogComponent } from './tiers-delete-dialog.component';
import { tiersRoute } from './tiers.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(tiersRoute)],
  declarations: [TiersComponent, TiersDetailComponent, TiersUpdateComponent, TiersDeleteDialogComponent],
  entryComponents: [TiersDeleteDialogComponent],
})
export class MooinbaseTiersModule {}
