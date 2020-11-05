import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { CompanyModuleComponent } from './company-module.component';
import { CompanyModuleDetailComponent } from './company-module-detail.component';
import { CompanyModuleUpdateComponent } from './company-module-update.component';
import { CompanyModuleDeleteDialogComponent } from './company-module-delete-dialog.component';
import { companyModuleRoute } from './company-module.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(companyModuleRoute)],
  declarations: [CompanyModuleComponent, CompanyModuleDetailComponent, CompanyModuleUpdateComponent, CompanyModuleDeleteDialogComponent],
  entryComponents: [CompanyModuleDeleteDialogComponent],
})
export class MooincompaniesCompanyModuleModule {}
