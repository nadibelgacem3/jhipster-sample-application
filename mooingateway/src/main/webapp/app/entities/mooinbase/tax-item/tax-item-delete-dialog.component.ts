import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaxItem } from 'app/shared/model/mooinbase/tax-item.model';
import { TaxItemService } from './tax-item.service';

@Component({
  templateUrl: './tax-item-delete-dialog.component.html',
})
export class TaxItemDeleteDialogComponent {
  taxItem?: ITaxItem;

  constructor(protected taxItemService: TaxItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taxItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taxItemListModification');
      this.activeModal.close();
    });
  }
}
