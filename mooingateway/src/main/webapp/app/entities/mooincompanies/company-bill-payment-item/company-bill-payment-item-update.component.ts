import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompanyBillPaymentItem, CompanyBillPaymentItem } from 'app/shared/model/mooincompanies/company-bill-payment-item.model';
import { CompanyBillPaymentItemService } from './company-bill-payment-item.service';
import { ICompanyBillPayment } from 'app/shared/model/mooincompanies/company-bill-payment.model';
import { CompanyBillPaymentService } from 'app/entities/mooincompanies/company-bill-payment/company-bill-payment.service';
import { ICompanyModule } from 'app/shared/model/mooincompanies/company-module.model';
import { CompanyModuleService } from 'app/entities/mooincompanies/company-module/company-module.service';

type SelectableEntity = ICompanyBillPayment | ICompanyModule;

@Component({
  selector: 'jhi-company-bill-payment-item-update',
  templateUrl: './company-bill-payment-item-update.component.html',
})
export class CompanyBillPaymentItemUpdateComponent implements OnInit {
  isSaving = false;
  companybillpayments: ICompanyBillPayment[] = [];
  companymodules: ICompanyModule[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.min(0)]],
    discountRate: [null, [Validators.min(0)]],
    totalHT: [null, [Validators.min(0)]],
    totalTVA: [null, [Validators.min(0)]],
    totaTTC: [null, [Validators.min(0)]],
    companyBillPayment: [],
    companyModule: [],
  });

  constructor(
    protected companyBillPaymentItemService: CompanyBillPaymentItemService,
    protected companyBillPaymentService: CompanyBillPaymentService,
    protected companyModuleService: CompanyModuleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyBillPaymentItem }) => {
      this.updateForm(companyBillPaymentItem);

      this.companyBillPaymentService
        .query()
        .subscribe((res: HttpResponse<ICompanyBillPayment[]>) => (this.companybillpayments = res.body || []));

      this.companyModuleService.query().subscribe((res: HttpResponse<ICompanyModule[]>) => (this.companymodules = res.body || []));
    });
  }

  updateForm(companyBillPaymentItem: ICompanyBillPaymentItem): void {
    this.editForm.patchValue({
      id: companyBillPaymentItem.id,
      quantity: companyBillPaymentItem.quantity,
      discountRate: companyBillPaymentItem.discountRate,
      totalHT: companyBillPaymentItem.totalHT,
      totalTVA: companyBillPaymentItem.totalTVA,
      totaTTC: companyBillPaymentItem.totaTTC,
      companyBillPayment: companyBillPaymentItem.companyBillPayment,
      companyModule: companyBillPaymentItem.companyModule,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyBillPaymentItem = this.createFromForm();
    if (companyBillPaymentItem.id !== undefined) {
      this.subscribeToSaveResponse(this.companyBillPaymentItemService.update(companyBillPaymentItem));
    } else {
      this.subscribeToSaveResponse(this.companyBillPaymentItemService.create(companyBillPaymentItem));
    }
  }

  private createFromForm(): ICompanyBillPaymentItem {
    return {
      ...new CompanyBillPaymentItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      discountRate: this.editForm.get(['discountRate'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      companyBillPayment: this.editForm.get(['companyBillPayment'])!.value,
      companyModule: this.editForm.get(['companyModule'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyBillPaymentItem>>): void {
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
