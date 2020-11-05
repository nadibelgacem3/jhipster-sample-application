import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICashierProduct, CashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';
import { CashierProductService } from './cashier-product.service';

@Component({
  selector: 'jhi-cashier-product-update',
  templateUrl: './cashier-product-update.component.html',
})
export class CashierProductUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    productID: [null, [Validators.required]],
    cashierProdName: [null, [Validators.required]],
    cashierProdQty: [null, [Validators.required]],
    cashierProdPurchaseUnitPrice: [null, [Validators.required, Validators.min(0)]],
    cashierProdSaleUnitPrice: [null, [Validators.required, Validators.min(0)]],
    cashierProdStockLimit: [],
    cashierProdStockLimitAlert: [],
  });

  constructor(protected cashierProductService: CashierProductService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierProduct }) => {
      this.updateForm(cashierProduct);
    });
  }

  updateForm(cashierProduct: ICashierProduct): void {
    this.editForm.patchValue({
      id: cashierProduct.id,
      productID: cashierProduct.productID,
      cashierProdName: cashierProduct.cashierProdName,
      cashierProdQty: cashierProduct.cashierProdQty,
      cashierProdPurchaseUnitPrice: cashierProduct.cashierProdPurchaseUnitPrice,
      cashierProdSaleUnitPrice: cashierProduct.cashierProdSaleUnitPrice,
      cashierProdStockLimit: cashierProduct.cashierProdStockLimit,
      cashierProdStockLimitAlert: cashierProduct.cashierProdStockLimitAlert,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cashierProduct = this.createFromForm();
    if (cashierProduct.id !== undefined) {
      this.subscribeToSaveResponse(this.cashierProductService.update(cashierProduct));
    } else {
      this.subscribeToSaveResponse(this.cashierProductService.create(cashierProduct));
    }
  }

  private createFromForm(): ICashierProduct {
    return {
      ...new CashierProduct(),
      id: this.editForm.get(['id'])!.value,
      productID: this.editForm.get(['productID'])!.value,
      cashierProdName: this.editForm.get(['cashierProdName'])!.value,
      cashierProdQty: this.editForm.get(['cashierProdQty'])!.value,
      cashierProdPurchaseUnitPrice: this.editForm.get(['cashierProdPurchaseUnitPrice'])!.value,
      cashierProdSaleUnitPrice: this.editForm.get(['cashierProdSaleUnitPrice'])!.value,
      cashierProdStockLimit: this.editForm.get(['cashierProdStockLimit'])!.value,
      cashierProdStockLimitAlert: this.editForm.get(['cashierProdStockLimitAlert'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICashierProduct>>): void {
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
