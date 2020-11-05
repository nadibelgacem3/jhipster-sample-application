import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICashierReceipt, CashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';
import { CashierReceiptService } from './cashier-receipt.service';
import { ICashier } from 'app/shared/model/mooincashier/cashier.model';
import { CashierService } from 'app/entities/mooincashier/cashier/cashier.service';
import { ICashierCostumer } from 'app/shared/model/mooincashier/cashier-costumer.model';
import { CashierCostumerService } from 'app/entities/mooincashier/cashier-costumer/cashier-costumer.service';

type SelectableEntity = ICashier | ICashierCostumer;

@Component({
  selector: 'jhi-cashier-receipt-update',
  templateUrl: './cashier-receipt-update.component.html',
})
export class CashierReceiptUpdateComponent implements OnInit {
  isSaving = false;
  cashiers: ICashier[] = [];
  cashiercostumers: ICashierCostumer[] = [];
  dateDp: any;
  dueDateDp: any;

  editForm = this.fb.group({
    id: [],
    number: [],
    reference: [],
    status: [],
    totalHT: [null, [Validators.required, Validators.min(0)]],
    totalTVA: [null, [Validators.required, Validators.min(0)]],
    totaTTC: [null, [Validators.required, Validators.min(0)]],
    date: [null, [Validators.required]],
    dueDate: [],
    isConverted: [],
    withTVA: [],
    withTax: [],
    cashier: [],
    cashierCostumer: [],
  });

  constructor(
    protected cashierReceiptService: CashierReceiptService,
    protected cashierService: CashierService,
    protected cashierCostumerService: CashierCostumerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierReceipt }) => {
      this.updateForm(cashierReceipt);

      this.cashierService.query().subscribe((res: HttpResponse<ICashier[]>) => (this.cashiers = res.body || []));

      this.cashierCostumerService.query().subscribe((res: HttpResponse<ICashierCostumer[]>) => (this.cashiercostumers = res.body || []));
    });
  }

  updateForm(cashierReceipt: ICashierReceipt): void {
    this.editForm.patchValue({
      id: cashierReceipt.id,
      number: cashierReceipt.number,
      reference: cashierReceipt.reference,
      status: cashierReceipt.status,
      totalHT: cashierReceipt.totalHT,
      totalTVA: cashierReceipt.totalTVA,
      totaTTC: cashierReceipt.totaTTC,
      date: cashierReceipt.date,
      dueDate: cashierReceipt.dueDate,
      isConverted: cashierReceipt.isConverted,
      withTVA: cashierReceipt.withTVA,
      withTax: cashierReceipt.withTax,
      cashier: cashierReceipt.cashier,
      cashierCostumer: cashierReceipt.cashierCostumer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cashierReceipt = this.createFromForm();
    if (cashierReceipt.id !== undefined) {
      this.subscribeToSaveResponse(this.cashierReceiptService.update(cashierReceipt));
    } else {
      this.subscribeToSaveResponse(this.cashierReceiptService.create(cashierReceipt));
    }
  }

  private createFromForm(): ICashierReceipt {
    return {
      ...new CashierReceipt(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      status: this.editForm.get(['status'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      date: this.editForm.get(['date'])!.value,
      dueDate: this.editForm.get(['dueDate'])!.value,
      isConverted: this.editForm.get(['isConverted'])!.value,
      withTVA: this.editForm.get(['withTVA'])!.value,
      withTax: this.editForm.get(['withTax'])!.value,
      cashier: this.editForm.get(['cashier'])!.value,
      cashierCostumer: this.editForm.get(['cashierCostumer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICashierReceipt>>): void {
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
