import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICashierApproItem, CashierApproItem } from 'app/shared/model/mooincashier/cashier-appro-item.model';
import { CashierApproItemService } from './cashier-appro-item.service';
import { ICashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';
import { CashierProductService } from 'app/entities/mooincashier/cashier-product/cashier-product.service';
import { ICashierAppro } from 'app/shared/model/mooincashier/cashier-appro.model';
import { CashierApproService } from 'app/entities/mooincashier/cashier-appro/cashier-appro.service';

type SelectableEntity = ICashierProduct | ICashierAppro;

@Component({
  selector: 'jhi-cashier-appro-item-update',
  templateUrl: './cashier-appro-item-update.component.html',
})
export class CashierApproItemUpdateComponent implements OnInit {
  isSaving = false;
  cashierproducts: ICashierProduct[] = [];
  cashierappros: ICashierAppro[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.min(0)]],
    discountRate: [null, [Validators.min(0)]],
    totalHT: [null, [Validators.min(0)]],
    totalTVA: [null, [Validators.min(0)]],
    totaTTC: [null, [Validators.min(0)]],
    cashierProduct: [],
    cashierAppro: [],
  });

  constructor(
    protected cashierApproItemService: CashierApproItemService,
    protected cashierProductService: CashierProductService,
    protected cashierApproService: CashierApproService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierApproItem }) => {
      this.updateForm(cashierApproItem);

      this.cashierProductService.query().subscribe((res: HttpResponse<ICashierProduct[]>) => (this.cashierproducts = res.body || []));

      this.cashierApproService.query().subscribe((res: HttpResponse<ICashierAppro[]>) => (this.cashierappros = res.body || []));
    });
  }

  updateForm(cashierApproItem: ICashierApproItem): void {
    this.editForm.patchValue({
      id: cashierApproItem.id,
      quantity: cashierApproItem.quantity,
      discountRate: cashierApproItem.discountRate,
      totalHT: cashierApproItem.totalHT,
      totalTVA: cashierApproItem.totalTVA,
      totaTTC: cashierApproItem.totaTTC,
      cashierProduct: cashierApproItem.cashierProduct,
      cashierAppro: cashierApproItem.cashierAppro,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cashierApproItem = this.createFromForm();
    if (cashierApproItem.id !== undefined) {
      this.subscribeToSaveResponse(this.cashierApproItemService.update(cashierApproItem));
    } else {
      this.subscribeToSaveResponse(this.cashierApproItemService.create(cashierApproItem));
    }
  }

  private createFromForm(): ICashierApproItem {
    return {
      ...new CashierApproItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      discountRate: this.editForm.get(['discountRate'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      cashierProduct: this.editForm.get(['cashierProduct'])!.value,
      cashierAppro: this.editForm.get(['cashierAppro'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICashierApproItem>>): void {
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
