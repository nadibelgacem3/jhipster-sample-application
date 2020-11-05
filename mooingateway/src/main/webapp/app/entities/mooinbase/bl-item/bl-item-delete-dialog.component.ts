import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBLItem } from 'app/shared/model/mooinbase/bl-item.model';
import { BLItemService } from './bl-item.service';

@Component({
  templateUrl: './bl-item-delete-dialog.component.html',
})
export class BLItemDeleteDialogComponent {
  bLItem?: IBLItem;

  constructor(protected bLItemService: BLItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bLItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bLItemListModification');
      this.activeModal.close();
    });
  }
}
