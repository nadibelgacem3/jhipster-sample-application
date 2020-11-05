import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICashierCostumer } from 'app/shared/model/mooincashier/cashier-costumer.model';
import { CashierCostumerService } from './cashier-costumer.service';

@Component({
  templateUrl: './cashier-costumer-delete-dialog.component.html',
})
export class CashierCostumerDeleteDialogComponent {
  cashierCostumer?: ICashierCostumer;

  constructor(
    protected cashierCostumerService: CashierCostumerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cashierCostumerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cashierCostumerListModification');
      this.activeModal.close();
    });
  }
}
