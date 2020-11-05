import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOfferOrder } from 'app/shared/model/mooinecommerce/offer-order.model';
import { OfferOrderService } from './offer-order.service';

@Component({
  templateUrl: './offer-order-delete-dialog.component.html',
})
export class OfferOrderDeleteDialogComponent {
  offerOrder?: IOfferOrder;

  constructor(
    protected offerOrderService: OfferOrderService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.offerOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('offerOrderListModification');
      this.activeModal.close();
    });
  }
}
