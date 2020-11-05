import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IOffer, Offer } from 'app/shared/model/mooinecommerce/offer.model';
import { OfferService } from './offer.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-offer-update',
  templateUrl: './offer-update.component.html',
})
export class OfferUpdateComponent implements OnInit {
  isSaving = false;
  dateBeginDp: any;
  dateEndDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    image: [],
    imageContentType: [],
    dateBegin: [],
    dateEnd: [],
    duration: [],
    status: [],
    productId: [],
    saleUnitPriceBeforeDiscount: [null, [Validators.min(0)]],
    discountRate: [null, [Validators.min(0)]],
    saleUnitPriceAfterDiscount: [null, [Validators.min(0)]],
    withTVA: [],
    withTax: [],
    isActivated: [],
    companyID: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected offerService: OfferService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offer }) => {
      this.updateForm(offer);
    });
  }

  updateForm(offer: IOffer): void {
    this.editForm.patchValue({
      id: offer.id,
      name: offer.name,
      description: offer.description,
      image: offer.image,
      imageContentType: offer.imageContentType,
      dateBegin: offer.dateBegin,
      dateEnd: offer.dateEnd,
      duration: offer.duration,
      status: offer.status,
      productId: offer.productId,
      saleUnitPriceBeforeDiscount: offer.saleUnitPriceBeforeDiscount,
      discountRate: offer.discountRate,
      saleUnitPriceAfterDiscount: offer.saleUnitPriceAfterDiscount,
      withTVA: offer.withTVA,
      withTax: offer.withTax,
      isActivated: offer.isActivated,
      companyID: offer.companyID,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('mooingatewayApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offer = this.createFromForm();
    if (offer.id !== undefined) {
      this.subscribeToSaveResponse(this.offerService.update(offer));
    } else {
      this.subscribeToSaveResponse(this.offerService.create(offer));
    }
  }

  private createFromForm(): IOffer {
    return {
      ...new Offer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      dateBegin: this.editForm.get(['dateBegin'])!.value,
      dateEnd: this.editForm.get(['dateEnd'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      status: this.editForm.get(['status'])!.value,
      productId: this.editForm.get(['productId'])!.value,
      saleUnitPriceBeforeDiscount: this.editForm.get(['saleUnitPriceBeforeDiscount'])!.value,
      discountRate: this.editForm.get(['discountRate'])!.value,
      saleUnitPriceAfterDiscount: this.editForm.get(['saleUnitPriceAfterDiscount'])!.value,
      withTVA: this.editForm.get(['withTVA'])!.value,
      withTax: this.editForm.get(['withTax'])!.value,
      isActivated: this.editForm.get(['isActivated'])!.value,
      companyID: this.editForm.get(['companyID'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffer>>): void {
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
}
