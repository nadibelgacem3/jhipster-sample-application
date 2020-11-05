import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { TaxComponent } from './tax.component';
import { TaxDetailComponent } from './tax-detail.component';
import { TaxUpdateComponent } from './tax-update.component';
import { TaxDeleteDialogComponent } from './tax-delete-dialog.component';
import { taxRoute } from './tax.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(taxRoute)],
  declarations: [TaxComponent, TaxDetailComponent, TaxUpdateComponent, TaxDeleteDialogComponent],
  entryComponents: [TaxDeleteDialogComponent],
})
export class MooincompaniesTaxModule {}
