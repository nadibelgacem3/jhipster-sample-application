import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyLocation } from 'app/shared/model/mooincompanies/company-location.model';
import { CompanyLocationService } from './company-location.service';

@Component({
  templateUrl: './company-location-delete-dialog.component.html',
})
export class CompanyLocationDeleteDialogComponent {
  companyLocation?: ICompanyLocation;

  constructor(
    protected companyLocationService: CompanyLocationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyLocationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('companyLocationListModification');
      this.activeModal.close();
    });
  }
}
