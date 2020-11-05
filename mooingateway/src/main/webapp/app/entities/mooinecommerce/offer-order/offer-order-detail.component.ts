import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOfferOrder } from 'app/shared/model/mooinecommerce/offer-order.model';

@Component({
  selector: 'jhi-offer-order-detail',
  templateUrl: './offer-order-detail.component.html',
})
export class OfferOrderDetailComponent implements OnInit {
  offerOrder: IOfferOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offerOrder }) => (this.offerOrder = offerOrder));
  }

  previousState(): void {
    window.history.back();
  }
}
