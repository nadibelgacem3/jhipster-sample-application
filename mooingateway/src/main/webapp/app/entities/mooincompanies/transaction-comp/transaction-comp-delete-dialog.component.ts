import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionComp } from 'app/shared/model/mooincompanies/transaction-comp.model';
import { TransactionCompService } from './transaction-comp.service';

@Component({
  templateUrl: './transaction-comp-delete-dialog.component.html',
})
export class TransactionCompDeleteDialogComponent {
  transactionComp?: ITransactionComp;

  constructor(
    protected transactionCompService: TransactionCompService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionCompService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transactionCompListModification');
      this.activeModal.close();
    });
  }
}
