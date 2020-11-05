import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITVAItem } from 'app/shared/model/mooinbase/tva-item.model';
import { TVAItemService } from './tva-item.service';

@Component({
  templateUrl: './tva-item-delete-dialog.component.html',
})
export class TVAItemDeleteDialogComponent {
  tVAItem?: ITVAItem;

  constructor(protected tVAItemService: TVAItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tVAItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tVAItemListModification');
      this.activeModal.close();
    });
  }
}
