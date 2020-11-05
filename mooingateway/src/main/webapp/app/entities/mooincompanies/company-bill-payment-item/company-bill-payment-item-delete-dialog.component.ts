import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyBillPaymentItem } from 'app/shared/model/mooincompanies/company-bill-payment-item.model';
import { CompanyBillPaymentItemService } from './company-bill-payment-item.service';

@Component({
  templateUrl: './company-bill-payment-item-delete-dialog.component.html',
})
export class CompanyBillPaymentItemDeleteDialogComponent {
  companyBillPaymentItem?: ICompanyBillPaymentItem;

  constructor(
    protected companyBillPaymentItemService: CompanyBillPaymentItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyBillPaymentItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('companyBillPaymentItemListModification');
      this.activeModal.close();
    });
  }
}
