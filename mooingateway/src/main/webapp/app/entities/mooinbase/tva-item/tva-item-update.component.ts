import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITVAItem, TVAItem } from 'app/shared/model/mooinbase/tva-item.model';
import { TVAItemService } from './tva-item.service';
import { IProduct } from 'app/shared/model/mooinbase/product.model';
import { ProductService } from 'app/entities/mooinbase/product/product.service';

@Component({
  selector: 'jhi-tva-item-update',
  templateUrl: './tva-item-update.component.html',
})
export class TVAItemUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    percentageValue: [null, [Validators.required]],
    companyID: [],
    tvaID: [],
    product: [],
  });

  constructor(
    protected tVAItemService: TVAItemService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tVAItem }) => {
      this.updateForm(tVAItem);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(tVAItem: ITVAItem): void {
    this.editForm.patchValue({
      id: tVAItem.id,
      name: tVAItem.name,
      percentageValue: tVAItem.percentageValue,
      companyID: tVAItem.companyID,
      tvaID: tVAItem.tvaID,
      product: tVAItem.product,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tVAItem = this.createFromForm();
    if (tVAItem.id !== undefined) {
      this.subscribeToSaveResponse(this.tVAItemService.update(tVAItem));
    } else {
      this.subscribeToSaveResponse(this.tVAItemService.create(tVAItem));
    }
  }

  private createFromForm(): ITVAItem {
    return {
      ...new TVAItem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      percentageValue: this.editForm.get(['percentageValue'])!.value,
      companyID: this.editForm.get(['companyID'])!.value,
      tvaID: this.editForm.get(['tvaID'])!.value,
      product: this.editForm.get(['product'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITVAItem>>): void {
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

  trackById(index: number, item: IProduct): any {
    return item.id;
  }
}
