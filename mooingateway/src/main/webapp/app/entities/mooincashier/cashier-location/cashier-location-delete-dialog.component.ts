import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICashierLocation } from 'app/shared/model/mooincashier/cashier-location.model';
import { CashierLocationService } from './cashier-location.service';

@Component({
  templateUrl: './cashier-location-delete-dialog.component.html',
})
export class CashierLocationDeleteDialogComponent {
  cashierLocation?: ICashierLocation;

  constructor(
    protected cashierLocationService: CashierLocationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cashierLocationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cashierLocationListModification');
      this.activeModal.close();
    });
  }
}
