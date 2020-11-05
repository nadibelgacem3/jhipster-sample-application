import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeLocation } from 'app/shared/model/mooincompanies/employee-location.model';
import { EmployeeLocationService } from './employee-location.service';

@Component({
  templateUrl: './employee-location-delete-dialog.component.html',
})
export class EmployeeLocationDeleteDialogComponent {
  employeeLocation?: IEmployeeLocation;

  constructor(
    protected employeeLocationService: EmployeeLocationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeeLocationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employeeLocationListModification');
      this.activeModal.close();
    });
  }
}
