import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAvoirItem, AvoirItem } from 'app/shared/model/mooinbase/avoir-item.model';
import { AvoirItemService } from './avoir-item.service';
import { IProduct } from 'app/shared/model/mooinbase/product.model';
import { ProductService } from 'app/entities/mooinbase/product/product.service';
import { IAvoir } from 'app/shared/model/mooinbase/avoir.model';
import { AvoirService } from 'app/entities/mooinbase/avoir/avoir.service';

type SelectableEntity = IProduct | IAvoir;

@Component({
  selector: 'jhi-avoir-item-update',
  templateUrl: './avoir-item-update.component.html',
})
export class AvoirItemUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  avoirs: IAvoir[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.min(0)]],
    discountRate: [null, [Validators.min(0)]],
    totalHT: [null, [Validators.min(0)]],
    totalTVA: [null, [Validators.min(0)]],
    totaTTC: [null, [Validators.min(0)]],
    product: [],
    avoir: [],
  });

  constructor(
    protected avoirItemService: AvoirItemService,
    protected productService: ProductService,
    protected avoirService: AvoirService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avoirItem }) => {
      this.updateForm(avoirItem);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.avoirService.query().subscribe((res: HttpResponse<IAvoir[]>) => (this.avoirs = res.body || []));
    });
  }

  updateForm(avoirItem: IAvoirItem): void {
    this.editForm.patchValue({
      id: avoirItem.id,
      quantity: avoirItem.quantity,
      discountRate: avoirItem.discountRate,
      totalHT: avoirItem.totalHT,
      totalTVA: avoirItem.totalTVA,
      totaTTC: avoirItem.totaTTC,
      product: avoirItem.product,
      avoir: avoirItem.avoir,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avoirItem = this.createFromForm();
    if (avoirItem.id !== undefined) {
      this.subscribeToSaveResponse(this.avoirItemService.update(avoirItem));
    } else {
      this.subscribeToSaveResponse(this.avoirItemService.create(avoirItem));
    }
  }

  private createFromForm(): IAvoirItem {
    return {
      ...new AvoirItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      discountRate: this.editForm.get(['discountRate'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      product: this.editForm.get(['product'])!.value,
      avoir: this.editForm.get(['avoir'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvoirItem>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
