import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICashierReceiptPay } from 'app/shared/model/mooincashier/cashier-receipt-pay.model';
import { CashierReceiptPayService } from './cashier-receipt-pay.service';

@Component({
  templateUrl: './cashier-receipt-pay-delete-dialog.component.html',
})
export class CashierReceiptPayDeleteDialogComponent {
  cashierReceiptPay?: ICashierReceiptPay;

  constructor(
    protected cashierReceiptPayService: CashierReceiptPayService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cashierReceiptPayService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cashierReceiptPayListModification');
      this.activeModal.close();
    });
  }
}
