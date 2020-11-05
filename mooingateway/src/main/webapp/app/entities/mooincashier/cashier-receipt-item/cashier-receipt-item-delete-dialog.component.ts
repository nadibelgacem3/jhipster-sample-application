import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICashierReceiptItem } from 'app/shared/model/mooincashier/cashier-receipt-item.model';
import { CashierReceiptItemService } from './cashier-receipt-item.service';

@Component({
  templateUrl: './cashier-receipt-item-delete-dialog.component.html',
})
export class CashierReceiptItemDeleteDialogComponent {
  cashierReceiptItem?: ICashierReceiptItem;

  constructor(
    protected cashierReceiptItemService: CashierReceiptItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cashierReceiptItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cashierReceiptItemListModification');
      this.activeModal.close();
    });
  }
}
