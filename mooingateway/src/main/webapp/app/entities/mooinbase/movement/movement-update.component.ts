import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMovement, Movement } from 'app/shared/model/mooinbase/movement.model';
import { MovementService } from './movement.service';
import { IProduct } from 'app/shared/model/mooinbase/product.model';
import { ProductService } from 'app/entities/mooinbase/product/product.service';

@Component({
  selector: 'jhi-movement-update',
  templateUrl: './movement-update.component.html',
})
export class MovementUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
    reason: [null, [Validators.required]],
    date: [null, [Validators.required]],
    billID: [],
    tiersID: [],
    quantity: [null, [Validators.required]],
    companyUserID: [],
    product: [],
  });

  constructor(
    protected movementService: MovementService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ movement }) => {
      if (!movement.id) {
        const today = moment().startOf('day');
        movement.date = today;
      }

      this.updateForm(movement);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(movement: IMovement): void {
    this.editForm.patchValue({
      id: movement.id,
      type: movement.type,
      reason: movement.reason,
      date: movement.date ? movement.date.format(DATE_TIME_FORMAT) : null,
      billID: movement.billID,
      tiersID: movement.tiersID,
      quantity: movement.quantity,
      companyUserID: movement.companyUserID,
      product: movement.product,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const movement = this.createFromForm();
    if (movement.id !== undefined) {
      this.subscribeToSaveResponse(this.movementService.update(movement));
    } else {
      this.subscribeToSaveResponse(this.movementService.create(movement));
    }
  }

  private createFromForm(): IMovement {
    return {
      ...new Movement(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      billID: this.editForm.get(['billID'])!.value,
      tiersID: this.editForm.get(['tiersID'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      companyUserID: this.editForm.get(['companyUserID'])!.value,
      product: this.editForm.get(['product'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovement>>): void {
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
