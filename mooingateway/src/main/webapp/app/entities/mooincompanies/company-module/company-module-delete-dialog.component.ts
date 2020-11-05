import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyModule } from 'app/shared/model/mooincompanies/company-module.model';
import { CompanyModuleService } from './company-module.service';

@Component({
  templateUrl: './company-module-delete-dialog.component.html',
})
export class CompanyModuleDeleteDialogComponent {
  companyModule?: ICompanyModule;

  constructor(
    protected companyModuleService: CompanyModuleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyModuleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('companyModuleListModification');
      this.activeModal.close();
    });
  }
}
