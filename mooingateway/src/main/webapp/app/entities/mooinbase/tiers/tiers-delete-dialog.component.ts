import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITiers } from 'app/shared/model/mooinbase/tiers.model';
import { TiersService } from './tiers.service';

@Component({
  templateUrl: './tiers-delete-dialog.component.html',
})
export class TiersDeleteDialogComponent {
  tiers?: ITiers;

  constructor(protected tiersService: TiersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tiersService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tiersListModification');
      this.activeModal.close();
    });
  }
}
