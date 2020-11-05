import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CompanyLocationComponent } from './company-location.component';
import { CompanyLocationDetailComponent } from './company-location-detail.component';
import { CompanyLocationUpdateComponent } from './company-location-update.component';
import { CompanyLocationDeleteDialogComponent } from './company-location-delete-dialog.component';
import { companyLocationRoute } from './company-location.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(companyLocationRoute)],
  declarations: [
    CompanyLocationComponent,
    CompanyLocationDetailComponent,
    CompanyLocationUpdateComponent,
    CompanyLocationDeleteDialogComponent,
  ],
  entryComponents: [CompanyLocationDeleteDialogComponent],
})
export class MooincompaniesCompanyLocationModule {}
