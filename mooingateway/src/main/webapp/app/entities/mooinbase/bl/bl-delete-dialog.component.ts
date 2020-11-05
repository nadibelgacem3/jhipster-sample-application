import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBL } from 'app/shared/model/mooinbase/bl.model';
import { BLService } from './bl.service';

@Component({
  templateUrl: './bl-delete-dialog.component.html',
})
export class BLDeleteDialogComponent {
  bL?: IBL;

  constructor(protected bLService: BLService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bLService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bLListModification');
      this.activeModal.close();
    });
  }
}
