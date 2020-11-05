import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITiersLocation } from 'app/shared/model/mooinbase/tiers-location.model';
import { TiersLocationService } from './tiers-location.service';

@Component({
  templateUrl: './tiers-location-delete-dialog.component.html',
})
export class TiersLocationDeleteDialogComponent {
  tiersLocation?: ITiersLocation;

  constructor(
    protected tiersLocationService: TiersLocationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tiersLocationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tiersLocationListModification');
      this.activeModal.close();
    });
  }
}
