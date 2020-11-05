import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IInvoicePaymentHistory } from 'app/shared/model/mooinbase/invoice-payment-history.model';

@Component({
  selector: 'jhi-invoice-payment-history-detail',
  templateUrl: './invoice-payment-history-detail.component.html',
})
export class InvoicePaymentHistoryDetailComponent implements OnInit {
  invoicePaymentHistory: IInvoicePaymentHistory | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoicePaymentHistory }) => (this.invoicePaymentHistory = invoicePaymentHistory));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
