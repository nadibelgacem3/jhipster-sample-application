import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeePayment } from 'app/shared/model/mooincompanies/employee-payment.model';
import { EmployeePaymentService } from './employee-payment.service';

@Component({
  templateUrl: './employee-payment-delete-dialog.component.html',
})
export class EmployeePaymentDeleteDialogComponent {
  employeePayment?: IEmployeePayment;

  constructor(
    protected employeePaymentService: EmployeePaymentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeePaymentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employeePaymentListModification');
      this.activeModal.close();
    });
  }
}
