import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICashier } from 'app/shared/model/mooincashier/cashier.model';
import { CashierService } from './cashier.service';

@Component({
  templateUrl: './cashier-delete-dialog.component.html',
})
export class CashierDeleteDialogComponent {
  cashier?: ICashier;

  constructor(protected cashierService: CashierService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cashierService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cashierListModification');
      this.activeModal.close();
    });
  }
}
