import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICashierReceiptPay } from 'app/shared/model/mooincashier/cashier-receipt-pay.model';

@Component({
  selector: 'jhi-cashier-receipt-pay-detail',
  templateUrl: './cashier-receipt-pay-detail.component.html',
})
export class CashierReceiptPayDetailComponent implements OnInit {
  cashierReceiptPay: ICashierReceiptPay | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierReceiptPay }) => (this.cashierReceiptPay = cashierReceiptPay));
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
