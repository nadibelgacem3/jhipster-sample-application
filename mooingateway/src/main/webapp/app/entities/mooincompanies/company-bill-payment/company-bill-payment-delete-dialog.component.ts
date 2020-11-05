import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyBillPayment } from 'app/shared/model/mooincompanies/company-bill-payment.model';
import { CompanyBillPaymentService } from './company-bill-payment.service';

@Component({
  templateUrl: './company-bill-payment-delete-dialog.component.html',
})
export class CompanyBillPaymentDeleteDialogComponent {
  companyBillPayment?: ICompanyBillPayment;

  constructor(
    protected companyBillPaymentService: CompanyBillPaymentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyBillPaymentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('companyBillPaymentListModification');
      this.activeModal.close();
    });
  }
}
