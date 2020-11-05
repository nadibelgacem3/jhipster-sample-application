import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICashierAppro } from 'app/shared/model/mooincashier/cashier-appro.model';
import { CashierApproService } from './cashier-appro.service';

@Component({
  templateUrl: './cashier-appro-delete-dialog.component.html',
})
export class CashierApproDeleteDialogComponent {
  cashierAppro?: ICashierAppro;

  constructor(
    protected cashierApproService: CashierApproService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cashierApproService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cashierApproListModification');
      this.activeModal.close();
    });
  }
}
