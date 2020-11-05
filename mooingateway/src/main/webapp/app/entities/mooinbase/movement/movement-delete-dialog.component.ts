import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovement } from 'app/shared/model/mooinbase/movement.model';
import { MovementService } from './movement.service';

@Component({
  templateUrl: './movement-delete-dialog.component.html',
})
export class MovementDeleteDialogComponent {
  movement?: IMovement;

  constructor(protected movementService: MovementService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.movementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('movementListModification');
      this.activeModal.close();
    });
  }
}
