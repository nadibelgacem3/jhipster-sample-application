import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICashier, Cashier } from 'app/shared/model/mooincashier/cashier.model';
import { CashierService } from './cashier.service';
import { ICashierLocation } from 'app/shared/model/mooincashier/cashier-location.model';
import { CashierLocationService } from 'app/entities/mooincashier/cashier-location/cashier-location.service';

@Component({
  selector: 'jhi-cashier-update',
  templateUrl: './cashier-update.component.html',
})
export class CashierUpdateComponent implements OnInit {
  isSaving = false;
  cashierlocations: ICashierLocation[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    withTicket: [],
    withTVA: [],
    withTax: [],
    withAppro: [],
    themeColor: [],
    isActivated: [],
    companyID: [],
    cashierLocation: [],
  });

  constructor(
    protected cashierService: CashierService,
    protected cashierLocationService: CashierLocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashier }) => {
      this.updateForm(cashier);

      this.cashierLocationService
        .query({ filter: 'cashier-is-null' })
        .pipe(
          map((res: HttpResponse<ICashierLocation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICashierLocation[]) => {
          if (!cashier.cashierLocation || !cashier.cashierLocation.id) {
            this.cashierlocations = resBody;
          } else {
            this.cashierLocationService
              .find(cashier.cashierLocation.id)
              .pipe(
                map((subRes: HttpResponse<ICashierLocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICashierLocation[]) => (this.cashierlocations = concatRes));
          }
        });
    });
  }

  updateForm(cashier: ICashier): void {
    this.editForm.patchValue({
      id: cashier.id,
      name: cashier.name,
      withTicket: cashier.withTicket,
      withTVA: cashier.withTVA,
      withTax: cashier.withTax,
      withAppro: cashier.withAppro,
      themeColor: cashier.themeColor,
      isActivated: cashier.isActivated,
      companyID: cashier.companyID,
      cashierLocation: cashier.cashierLocation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cashier = this.createFromForm();
    if (cashier.id !== undefined) {
      this.subscribeToSaveResponse(this.cashierService.update(cashier));
    } else {
      this.subscribeToSaveResponse(this.cashierService.create(cashier));
    }
  }

  private createFromForm(): ICashier {
    return {
      ...new Cashier(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      withTicket: this.editForm.get(['withTicket'])!.value,
      withTVA: this.editForm.get(['withTVA'])!.value,
      withTax: this.editForm.get(['withTax'])!.value,
      withAppro: this.editForm.get(['withAppro'])!.value,
      themeColor: this.editForm.get(['themeColor'])!.value,
      isActivated: this.editForm.get(['isActivated'])!.value,
      companyID: this.editForm.get(['companyID'])!.value,
      cashierLocation: this.editForm.get(['cashierLocation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICashier>>): void {
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

  trackById(index: number, item: ICashierLocation): any {
    return item.id;
  }
}
