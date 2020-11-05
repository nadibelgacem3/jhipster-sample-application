import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInvoice, Invoice } from 'app/shared/model/mooinbase/invoice.model';
import { InvoiceService } from './invoice.service';
import { ITiers } from 'app/shared/model/mooinbase/tiers.model';
import { TiersService } from 'app/entities/mooinbase/tiers/tiers.service';

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html',
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving = false;
  tiers: ITiers[] = [];
  dateDp: any;
  dueDateDp: any;

  editForm = this.fb.group({
    id: [],
    number: [],
    reference: [],
    status: [],
    type: [],
    totalHT: [null, [Validators.required, Validators.min(0)]],
    totalTVA: [null, [Validators.required, Validators.min(0)]],
    totaTTC: [null, [Validators.required, Validators.min(0)]],
    date: [null, [Validators.required]],
    dueDate: [],
    companyID: [],
    tiers: [],
  });

  constructor(
    protected invoiceService: InvoiceService,
    protected tiersService: TiersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.updateForm(invoice);

      this.tiersService.query().subscribe((res: HttpResponse<ITiers[]>) => (this.tiers = res.body || []));
    });
  }

  updateForm(invoice: IInvoice): void {
    this.editForm.patchValue({
      id: invoice.id,
      number: invoice.number,
      reference: invoice.reference,
      status: invoice.status,
      type: invoice.type,
      totalHT: invoice.totalHT,
      totalTVA: invoice.totalTVA,
      totaTTC: invoice.totaTTC,
      date: invoice.date,
      dueDate: invoice.dueDate,
      companyID: invoice.companyID,
      tiers: invoice.tiers,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  private createFromForm(): IInvoice {
    return {
      ...new Invoice(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      status: this.editForm.get(['status'])!.value,
      type: this.editForm.get(['type'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      date: this.editForm.get(['date'])!.value,
      dueDate: this.editForm.get(['dueDate'])!.value,
      companyID: this.editForm.get(['companyID'])!.value,
      tiers: this.editForm.get(['tiers'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>): void {
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

  trackById(index: number, item: ITiers): any {
    return item.id;
  }
}
