import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICashierApproItem } from 'app/shared/model/mooincashier/cashier-appro-item.model';

@Component({
  selector: 'jhi-cashier-appro-item-detail',
  templateUrl: './cashier-appro-item-detail.component.html',
})
export class CashierApproItemDetailComponent implements OnInit {
  cashierApproItem: ICashierApproItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierApproItem }) => (this.cashierApproItem = cashierApproItem));
  }

  previousState(): void {
    window.history.back();
  }
}
