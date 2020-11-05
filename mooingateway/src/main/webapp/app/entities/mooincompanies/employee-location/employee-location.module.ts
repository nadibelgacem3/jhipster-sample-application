import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { EmployeeLocationComponent } from './employee-location.component';
import { EmployeeLocationDetailComponent } from './employee-location-detail.component';
import { EmployeeLocationUpdateComponent } from './employee-location-update.component';
import { EmployeeLocationDeleteDialogComponent } from './employee-location-delete-dialog.component';
import { employeeLocationRoute } from './employee-location.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(employeeLocationRoute)],
  declarations: [
    EmployeeLocationComponent,
    EmployeeLocationDetailComponent,
    EmployeeLocationUpdateComponent,
    EmployeeLocationDeleteDialogComponent,
  ],
  entryComponents: [EmployeeLocationDeleteDialogComponent],
})
export class MooincompaniesEmployeeLocationModule {}
