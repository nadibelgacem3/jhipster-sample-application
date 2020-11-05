import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBLItem, BLItem } from 'app/shared/model/mooinbase/bl-item.model';
import { BLItemService } from './bl-item.service';
import { IProduct } from 'app/shared/model/mooinbase/product.model';
import { ProductService } from 'app/entities/mooinbase/product/product.service';
import { IBL } from 'app/shared/model/mooinbase/bl.model';
import { BLService } from 'app/entities/mooinbase/bl/bl.service';

type SelectableEntity = IProduct | IBL;

@Component({
  selector: 'jhi-bl-item-update',
  templateUrl: './bl-item-update.component.html',
})
export class BLItemUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  bls: IBL[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.min(0)]],
    discountRate: [null, [Validators.min(0)]],
    totalHT: [null, [Validators.min(0)]],
    totalTVA: [null, [Validators.min(0)]],
    totaTTC: [null, [Validators.min(0)]],
    product: [],
    bl: [],
  });

  constructor(
    protected bLItemService: BLItemService,
    protected productService: ProductService,
    protected bLService: BLService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bLItem }) => {
      this.updateForm(bLItem);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.bLService.query().subscribe((res: HttpResponse<IBL[]>) => (this.bls = res.body || []));
    });
  }

  updateForm(bLItem: IBLItem): void {
    this.editForm.patchValue({
      id: bLItem.id,
      quantity: bLItem.quantity,
      discountRate: bLItem.discountRate,
      totalHT: bLItem.totalHT,
      totalTVA: bLItem.totalTVA,
      totaTTC: bLItem.totaTTC,
      product: bLItem.product,
      bl: bLItem.bl,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bLItem = this.createFromForm();
    if (bLItem.id !== undefined) {
      this.subscribeToSaveResponse(this.bLItemService.update(bLItem));
    } else {
      this.subscribeToSaveResponse(this.bLItemService.create(bLItem));
    }
  }

  private createFromForm(): IBLItem {
    return {
      ...new BLItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      discountRate: this.editForm.get(['discountRate'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      product: this.editForm.get(['product'])!.value,
      bl: this.editForm.get(['bl'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBLItem>>): void {
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
