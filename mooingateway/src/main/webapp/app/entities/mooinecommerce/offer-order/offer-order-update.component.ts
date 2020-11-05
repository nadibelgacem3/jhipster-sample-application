import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOfferOrder, OfferOrder } from 'app/shared/model/mooinecommerce/offer-order.model';
import { OfferOrderService } from './offer-order.service';
import { IOffer } from 'app/shared/model/mooinecommerce/offer.model';
import { OfferService } from 'app/entities/mooinecommerce/offer/offer.service';

@Component({
  selector: 'jhi-offer-order-update',
  templateUrl: './offer-order-update.component.html',
})
export class OfferOrderUpdateComponent implements OnInit {
  isSaving = false;
  offers: IOffer[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required, Validators.min(1)]],
    totalHT: [null, [Validators.min(0)]],
    totalTVA: [null, [Validators.min(0)]],
    totaTTC: [null, [Validators.min(0)]],
    date: [null, [Validators.required]],
    status: [null, [Validators.required]],
    requesterOfferID: [],
    offer: [],
  });

  constructor(
    protected offerOrderService: OfferOrderService,
    protected offerService: OfferService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offerOrder }) => {
      this.updateForm(offerOrder);

      this.offerService.query().subscribe((res: HttpResponse<IOffer[]>) => (this.offers = res.body || []));
    });
  }

  updateForm(offerOrder: IOfferOrder): void {
    this.editForm.patchValue({
      id: offerOrder.id,
      quantity: offerOrder.quantity,
      totalHT: offerOrder.totalHT,
      totalTVA: offerOrder.totalTVA,
      totaTTC: offerOrder.totaTTC,
      date: offerOrder.date,
      status: offerOrder.status,
      requesterOfferID: offerOrder.requesterOfferID,
      offer: offerOrder.offer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offerOrder = this.createFromForm();
    if (offerOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.offerOrderService.update(offerOrder));
    } else {
      this.subscribeToSaveResponse(this.offerOrderService.create(offerOrder));
    }
  }

  private createFromForm(): IOfferOrder {
    return {
      ...new OfferOrder(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      date: this.editForm.get(['date'])!.value,
      status: this.editForm.get(['status'])!.value,
      requesterOfferID: this.editForm.get(['requesterOfferID'])!.value,
      offer: this.editForm.get(['offer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOfferOrder>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IOffer): any {
    return item.id;
  }
}
