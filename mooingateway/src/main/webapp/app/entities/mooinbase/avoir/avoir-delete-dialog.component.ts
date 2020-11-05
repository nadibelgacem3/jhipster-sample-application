import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvoir } from 'app/shared/model/mooinbase/avoir.model';
import { AvoirService } from './avoir.service';

@Component({
  templateUrl: './avoir-delete-dialog.component.html',
})
export class AvoirDeleteDialogComponent {
  avoir?: IAvoir;

  constructor(protected avoirService: AvoirService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avoirService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avoirListModification');
      this.activeModal.close();
    });
  }
}
