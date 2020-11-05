import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { TransactionCompComponent } from './transaction-comp.component';
import { TransactionCompDetailComponent } from './transaction-comp-detail.component';
import { TransactionCompUpdateComponent } from './transaction-comp-update.component';
import { TransactionCompDeleteDialogComponent } from './transaction-comp-delete-dialog.component';
import { transactionCompRoute } from './transaction-comp.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(transactionCompRoute)],
  declarations: [
    TransactionCompComponent,
    TransactionCompDetailComponent,
    TransactionCompUpdateComponent,
    TransactionCompDeleteDialogComponent,
  ],
  entryComponents: [TransactionCompDeleteDialogComponent],
})
export class MooincompaniesTransactionCompModule {}
