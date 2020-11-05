import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { TiersBankCheckComponent } from './tiers-bank-check.component';
import { TiersBankCheckDetailComponent } from './tiers-bank-check-detail.component';
import { TiersBankCheckUpdateComponent } from './tiers-bank-check-update.component';
import { TiersBankCheckDeleteDialogComponent } from './tiers-bank-check-delete-dialog.component';
import { tiersBankCheckRoute } from './tiers-bank-check.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(tiersBankCheckRoute)],
  declarations: [
    TiersBankCheckComponent,
    TiersBankCheckDetailComponent,
    TiersBankCheckUpdateComponent,
    TiersBankCheckDeleteDialogComponent,
  ],
  entryComponents: [TiersBankCheckDeleteDialogComponent],
})
export class MooinbaseTiersBankCheckModule {}
