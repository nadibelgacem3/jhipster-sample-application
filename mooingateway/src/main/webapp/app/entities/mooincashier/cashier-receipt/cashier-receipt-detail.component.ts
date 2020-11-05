import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';

@Component({
  selector: 'jhi-cashier-receipt-detail',
  templateUrl: './cashier-receipt-detail.component.html',
})
export class CashierReceiptDetailComponent implements OnInit {
  cashierReceipt: ICashierReceipt | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierReceipt }) => (this.cashierReceipt = cashierReceipt));
  }

  previousState(): void {
    window.history.back();
  }
}
