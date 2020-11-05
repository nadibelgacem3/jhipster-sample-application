import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICashierReceiptItem } from 'app/shared/model/mooincashier/cashier-receipt-item.model';

@Component({
  selector: 'jhi-cashier-receipt-item-detail',
  templateUrl: './cashier-receipt-item-detail.component.html',
})
export class CashierReceiptItemDetailComponent implements OnInit {
  cashierReceiptItem: ICashierReceiptItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierReceiptItem }) => (this.cashierReceiptItem = cashierReceiptItem));
  }

  previousState(): void {
    window.history.back();
  }
}
