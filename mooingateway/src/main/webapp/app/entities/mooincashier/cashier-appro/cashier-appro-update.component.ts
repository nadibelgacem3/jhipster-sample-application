import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICashierAppro, CashierAppro } from 'app/shared/model/mooincashier/cashier-appro.model';
import { CashierApproService } from './cashier-appro.service';
import { ICashier } from 'app/shared/model/mooincashier/cashier.model';
import { CashierService } from 'app/entities/mooincashier/cashier/cashier.service';

@Component({
  selector: 'jhi-cashier-appro-update',
  templateUrl: './cashier-appro-update.component.html',
})
export class CashierApproUpdateComponent implements OnInit {
  isSaving = false;
  cashiers: ICashier[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    number: [],
    status: [],
    totalHT: [null, [Validators.required, Validators.min(0)]],
    totalTVA: [null, [Validators.required, Validators.min(0)]],
    totaTTC: [null, [Validators.required, Validators.min(0)]],
    date: [null, [Validators.required]],
    isConverted: [],
    withTVA: [],
    withTax: [],
    cashier: [],
  });

  constructor(
    protected cashierApproService: CashierApproService,
    protected cashierService: CashierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierAppro }) => {
      this.updateForm(cashierAppro);

      this.cashierService.query().subscribe((res: HttpResponse<ICashier[]>) => (this.cashiers = res.body || []));
    });
  }

  updateForm(cashierAppro: ICashierAppro): void {
    this.editForm.patchValue({
      id: cashierAppro.id,
      number: cashierAppro.number,
      status: cashierAppro.status,
      totalHT: cashierAppro.totalHT,
      totalTVA: cashierAppro.totalTVA,
      totaTTC: cashierAppro.totaTTC,
      date: cashierAppro.date,
      isConverted: cashierAppro.isConverted,
      withTVA: cashierAppro.withTVA,
      withTax: cashierAppro.withTax,
      cashier: cashierAppro.cashier,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cashierAppro = this.createFromForm();
    if (cashierAppro.id !== undefined) {
      this.subscribeToSaveResponse(this.cashierApproService.update(cashierAppro));
    } else {
      this.subscribeToSaveResponse(this.cashierApproService.create(cashierAppro));
    }
  }

  private createFromForm(): ICashierAppro {
    return {
      ...new CashierAppro(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      status: this.editForm.get(['status'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      date: this.editForm.get(['date'])!.value,
      isConverted: this.editForm.get(['isConverted'])!.value,
      withTVA: this.editForm.get(['withTVA'])!.value,
      withTax: this.editForm.get(['withTax'])!.value,
      cashier: this.editForm.get(['cashier'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICashierAppro>>): void {
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

  trackById(index: number, item: ICashier): any {
    return item.id;
  }
}
