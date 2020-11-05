import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IInvoicePaymentHistory, InvoicePaymentHistory } from 'app/shared/model/mooinbase/invoice-payment-history.model';
import { InvoicePaymentHistoryService } from './invoice-payment-history.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IInvoice } from 'app/shared/model/mooinbase/invoice.model';
import { InvoiceService } from 'app/entities/mooinbase/invoice/invoice.service';

@Component({
  selector: 'jhi-invoice-payment-history-update',
  templateUrl: './invoice-payment-history-update.component.html',
})
export class InvoicePaymentHistoryUpdateComponent implements OnInit {
  isSaving = false;
  invoices: IInvoice[] = [];

  editForm = this.fb.group({
    id: [],
    details: [],
    amount: [null, [Validators.required, Validators.min(0)]],
    paymentDate: [],
    paymentMethod: [],
    bankCheckorTraitNumber: [],
    imageJustif: [],
    imageJustifContentType: [],
    invoice: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected invoicePaymentHistoryService: InvoicePaymentHistoryService,
    protected invoiceService: InvoiceService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoicePaymentHistory }) => {
      if (!invoicePaymentHistory.id) {
        const today = moment().startOf('day');
        invoicePaymentHistory.paymentDate = today;
      }

      this.updateForm(invoicePaymentHistory);

      this.invoiceService.query().subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body || []));
    });
  }

  updateForm(invoicePaymentHistory: IInvoicePaymentHistory): void {
    this.editForm.patchValue({
      id: invoicePaymentHistory.id,
      details: invoicePaymentHistory.details,
      amount: invoicePaymentHistory.amount,
      paymentDate: invoicePaymentHistory.paymentDate ? invoicePaymentHistory.paymentDate.format(DATE_TIME_FORMAT) : null,
      paymentMethod: invoicePaymentHistory.paymentMethod,
      bankCheckorTraitNumber: invoicePaymentHistory.bankCheckorTraitNumber,
      imageJustif: invoicePaymentHistory.imageJustif,
      imageJustifContentType: invoicePaymentHistory.imageJustifContentType,
      invoice: invoicePaymentHistory.invoice,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('mooingatewayApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoicePaymentHistory = this.createFromForm();
    if (invoicePaymentHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.invoicePaymentHistoryService.update(invoicePaymentHistory));
    } else {
      this.subscribeToSaveResponse(this.invoicePaymentHistoryService.create(invoicePaymentHistory));
    }
  }

  private createFromForm(): IInvoicePaymentHistory {
    return {
      ...new InvoicePaymentHistory(),
      id: this.editForm.get(['id'])!.value,
      details: this.editForm.get(['details'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      paymentDate: this.editForm.get(['paymentDate'])!.value
        ? moment(this.editForm.get(['paymentDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      bankCheckorTraitNumber: this.editForm.get(['bankCheckorTraitNumber'])!.value,
      imageJustifContentType: this.editForm.get(['imageJustifContentType'])!.value,
      imageJustif: this.editForm.get(['imageJustif'])!.value,
      invoice: this.editForm.get(['invoice'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoicePaymentHistory>>): void {
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

  trackById(index: number, item: IInvoice): any {
    return item.id;
  }
}
