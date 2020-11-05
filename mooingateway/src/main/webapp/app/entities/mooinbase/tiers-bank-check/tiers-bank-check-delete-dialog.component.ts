import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITiersBankCheck } from 'app/shared/model/mooinbase/tiers-bank-check.model';
import { TiersBankCheckService } from './tiers-bank-check.service';

@Component({
  templateUrl: './tiers-bank-check-delete-dialog.component.html',
})
export class TiersBankCheckDeleteDialogComponent {
  tiersBankCheck?: ITiersBankCheck;

  constructor(
    protected tiersBankCheckService: TiersBankCheckService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tiersBankCheckService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tiersBankCheckListModification');
      this.activeModal.close();
    });
  }
}
