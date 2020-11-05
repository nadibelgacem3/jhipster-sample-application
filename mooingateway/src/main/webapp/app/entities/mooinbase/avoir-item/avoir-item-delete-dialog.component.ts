import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvoirItem } from 'app/shared/model/mooinbase/avoir-item.model';
import { AvoirItemService } from './avoir-item.service';

@Component({
  templateUrl: './avoir-item-delete-dialog.component.html',
})
export class AvoirItemDeleteDialogComponent {
  avoirItem?: IAvoirItem;

  constructor(protected avoirItemService: AvoirItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avoirItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avoirItemListModification');
      this.activeModal.close();
    });
  }
}
