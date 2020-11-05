import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { MovementComponent } from './movement.component';
import { MovementDetailComponent } from './movement-detail.component';
import { MovementUpdateComponent } from './movement-update.component';
import { MovementDeleteDialogComponent } from './movement-delete-dialog.component';
import { movementRoute } from './movement.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(movementRoute)],
  declarations: [MovementComponent, MovementDetailComponent, MovementUpdateComponent, MovementDeleteDialogComponent],
  entryComponents: [MovementDeleteDialogComponent],
})
export class MooinbaseMovementModule {}
