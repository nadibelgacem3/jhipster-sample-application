import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';
import { CashierReceiptService } from './cashier-receipt.service';

@Component({
  templateUrl: './cashier-receipt-delete-dialog.component.html',
})
export class CashierReceiptDeleteDialogComponent {
  cashierReceipt?: ICashierReceipt;

  constructor(
    protected cashierReceiptService: CashierReceiptService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cashierReceiptService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cashierReceiptListModification');
      this.activeModal.close();
    });
  }
}
