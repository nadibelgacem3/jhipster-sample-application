import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICashierApproItem } from 'app/shared/model/mooincashier/cashier-appro-item.model';
import { CashierApproItemService } from './cashier-appro-item.service';

@Component({
  templateUrl: './cashier-appro-item-delete-dialog.component.html',
})
export class CashierApproItemDeleteDialogComponent {
  cashierApproItem?: ICashierApproItem;

  constructor(
    protected cashierApproItemService: CashierApproItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cashierApproItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cashierApproItemListModification');
      this.activeModal.close();
    });
  }
}
