import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITVA } from 'app/shared/model/mooincompanies/tva.model';
import { TVAService } from './tva.service';

@Component({
  templateUrl: './tva-delete-dialog.component.html',
})
export class TVADeleteDialogComponent {
  tVA?: ITVA;

  constructor(protected tVAService: TVAService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tVAService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tVAListModification');
      this.activeModal.close();
    });
  }
}
