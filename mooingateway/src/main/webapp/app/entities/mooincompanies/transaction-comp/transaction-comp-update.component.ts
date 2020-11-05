import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransactionComp, TransactionComp } from 'app/shared/model/mooincompanies/transaction-comp.model';
import { TransactionCompService } from './transaction-comp.service';
import { ICompany } from 'app/shared/model/mooincompanies/company.model';
import { CompanyService } from 'app/entities/mooincompanies/company/company.service';

@Component({
  selector: 'jhi-transaction-comp-update',
  templateUrl: './transaction-comp-update.component.html',
})
export class TransactionCompUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    number: [],
    details: [],
    type: [],
    paymentMethod: [],
    withTVA: [],
    totalHT: [null, [Validators.min(0)]],
    totalTVA: [null, [Validators.min(0)]],
    totaTTC: [null, [Validators.required, Validators.min(0)]],
    date: [null, [Validators.required]],
    company: [],
  });

  constructor(
    protected transactionCompService: TransactionCompService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionComp }) => {
      this.updateForm(transactionComp);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(transactionComp: ITransactionComp): void {
    this.editForm.patchValue({
      id: transactionComp.id,
      number: transactionComp.number,
      details: transactionComp.details,
      type: transactionComp.type,
      paymentMethod: transactionComp.paymentMethod,
      withTVA: transactionComp.withTVA,
      totalHT: transactionComp.totalHT,
      totalTVA: transactionComp.totalTVA,
      totaTTC: transactionComp.totaTTC,
      date: transactionComp.date,
      company: transactionComp.company,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionComp = this.createFromForm();
    if (transactionComp.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionCompService.update(transactionComp));
    } else {
      this.subscribeToSaveResponse(this.transactionCompService.create(transactionComp));
    }
  }

  private createFromForm(): ITransactionComp {
    return {
      ...new TransactionComp(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      details: this.editForm.get(['details'])!.value,
      type: this.editForm.get(['type'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      withTVA: this.editForm.get(['withTVA'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      date: this.editForm.get(['date'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionComp>>): void {
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

  trackById(index: number, item: ICompany): any {
    return item.id;
  }
}
