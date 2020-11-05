import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { EmployeePaymentComponent } from './employee-payment.component';
import { EmployeePaymentDetailComponent } from './employee-payment-detail.component';
import { EmployeePaymentUpdateComponent } from './employee-payment-update.component';
import { EmployeePaymentDeleteDialogComponent } from './employee-payment-delete-dialog.component';
import { employeePaymentRoute } from './employee-payment.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(employeePaymentRoute)],
  declarations: [
    EmployeePaymentComponent,
    EmployeePaymentDetailComponent,
    EmployeePaymentUpdateComponent,
    EmployeePaymentDeleteDialogComponent,
  ],
  entryComponents: [EmployeePaymentDeleteDialogComponent],
})
export class MooincompaniesEmployeePaymentModule {}
