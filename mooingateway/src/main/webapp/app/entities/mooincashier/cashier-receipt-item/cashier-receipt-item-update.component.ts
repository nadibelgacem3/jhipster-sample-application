import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICashierReceiptItem, CashierReceiptItem } from 'app/shared/model/mooincashier/cashier-receipt-item.model';
import { CashierReceiptItemService } from './cashier-receipt-item.service';
import { ICashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';
import { CashierProductService } from 'app/entities/mooincashier/cashier-product/cashier-product.service';
import { ICashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';
import { CashierReceiptService } from 'app/entities/mooincashier/cashier-receipt/cashier-receipt.service';

type SelectableEntity = ICashierProduct | ICashierReceipt;

@Component({
  selector: 'jhi-cashier-receipt-item-update',
  templateUrl: './cashier-receipt-item-update.component.html',
})
export class CashierReceiptItemUpdateComponent implements OnInit {
  isSaving = false;
  cashierproducts: ICashierProduct[] = [];
  cashierreceipts: ICashierReceipt[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.min(0)]],
    discountRate: [null, [Validators.min(0)]],
    totalHT: [null, [Validators.min(0)]],
    totalTVA: [null, [Validators.min(0)]],
    totaTTC: [null, [Validators.min(0)]],
    cashierProduct: [],
    cashierReceipt: [],
  });

  constructor(
    protected cashierReceiptItemService: CashierReceiptItemService,
    protected cashierProductService: CashierProductService,
    protected cashierReceiptService: CashierReceiptService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierReceiptItem }) => {
      this.updateForm(cashierReceiptItem);

      this.cashierProductService.query().subscribe((res: HttpResponse<ICashierProduct[]>) => (this.cashierproducts = res.body || []));

      this.cashierReceiptService.query().subscribe((res: HttpResponse<ICashierReceipt[]>) => (this.cashierreceipts = res.body || []));
    });
  }

  updateForm(cashierReceiptItem: ICashierReceiptItem): void {
    this.editForm.patchValue({
      id: cashierReceiptItem.id,
      quantity: cashierReceiptItem.quantity,
      discountRate: cashierReceiptItem.discountRate,
      totalHT: cashierReceiptItem.totalHT,
      totalTVA: cashierReceiptItem.totalTVA,
      totaTTC: cashierReceiptItem.totaTTC,
      cashierProduct: cashierReceiptItem.cashierProduct,
      cashierReceipt: cashierReceiptItem.cashierReceipt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cashierReceiptItem = this.createFromForm();
    if (cashierReceiptItem.id !== undefined) {
      this.subscribeToSaveResponse(this.cashierReceiptItemService.update(cashierReceiptItem));
    } else {
      this.subscribeToSaveResponse(this.cashierReceiptItemService.create(cashierReceiptItem));
    }
  }

  private createFromForm(): ICashierReceiptItem {
    return {
      ...new CashierReceiptItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      discountRate: this.editForm.get(['discountRate'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      cashierProduct: this.editForm.get(['cashierProduct'])!.value,
      cashierReceipt: this.editForm.get(['cashierReceipt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICashierReceiptItem>>): void {
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
