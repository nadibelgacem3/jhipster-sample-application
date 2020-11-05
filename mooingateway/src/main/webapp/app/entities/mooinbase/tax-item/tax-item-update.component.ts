import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITaxItem, TaxItem } from 'app/shared/model/mooinbase/tax-item.model';
import { TaxItemService } from './tax-item.service';
import { IProduct } from 'app/shared/model/mooinbase/product.model';
import { ProductService } from 'app/entities/mooinbase/product/product.service';

@Component({
  selector: 'jhi-tax-item-update',
  templateUrl: './tax-item-update.component.html',
})
export class TaxItemUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    isValued: [null, [Validators.required]],
    isPercentage: [null, [Validators.required]],
    value: [null, [Validators.required]],
    companyID: [],
    taxID: [],
    product: [],
  });

  constructor(
    protected taxItemService: TaxItemService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxItem }) => {
      this.updateForm(taxItem);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(taxItem: ITaxItem): void {
    this.editForm.patchValue({
      id: taxItem.id,
      name: taxItem.name,
      isValued: taxItem.isValued,
      isPercentage: taxItem.isPercentage,
      value: taxItem.value,
      companyID: taxItem.companyID,
      taxID: taxItem.taxID,
      product: taxItem.product,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taxItem = this.createFromForm();
    if (taxItem.id !== undefined) {
      this.subscribeToSaveResponse(this.taxItemService.update(taxItem));
    } else {
      this.subscribeToSaveResponse(this.taxItemService.create(taxItem));
    }
  }

  private createFromForm(): ITaxItem {
    return {
      ...new TaxItem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      isValued: this.editForm.get(['isValued'])!.value,
      isPercentage: this.editForm.get(['isPercentage'])!.value,
      value: this.editForm.get(['value'])!.value,
      companyID: this.editForm.get(['companyID'])!.value,
      taxID: this.editForm.get(['taxID'])!.value,
      product: this.editForm.get(['product'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxItem>>): void {
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
