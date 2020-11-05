import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductMark, ProductMark } from 'app/shared/model/mooinbase/product-mark.model';
import { ProductMarkService } from './product-mark.service';

@Component({
  selector: 'jhi-product-mark-update',
  templateUrl: './product-mark-update.component.html',
})
export class ProductMarkUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    companyID: [],
  });

  constructor(protected productMarkService: ProductMarkService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productMark }) => {
      this.updateForm(productMark);
    });
  }

  updateForm(productMark: IProductMark): void {
    this.editForm.patchValue({
      id: productMark.id,
      name: productMark.name,
      description: productMark.description,
      companyID: productMark.companyID,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productMark = this.createFromForm();
    if (productMark.id !== undefined) {
      this.subscribeToSaveResponse(this.productMarkService.update(productMark));
    } else {
      this.subscribeToSaveResponse(this.productMarkService.create(productMark));
    }
  }

  private createFromForm(): IProductMark {
    return {
      ...new ProductMark(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      companyID: this.editForm.get(['companyID'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductMark>>): void {
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
