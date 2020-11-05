import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoicePaymentHistory } from 'app/shared/model/mooinbase/invoice-payment-history.model';
import { InvoicePaymentHistoryService } from './invoice-payment-history.service';

@Component({
  templateUrl: './invoice-payment-history-delete-dialog.component.html',
})
export class InvoicePaymentHistoryDeleteDialogComponent {
  invoicePaymentHistory?: IInvoicePaymentHistory;

  constructor(
    protected invoicePaymentHistoryService: InvoicePaymentHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invoicePaymentHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('invoicePaymentHistoryListModification');
      this.activeModal.close();
    });
  }
}
