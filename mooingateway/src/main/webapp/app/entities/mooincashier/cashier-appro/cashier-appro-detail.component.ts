import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICashierAppro } from 'app/shared/model/mooincashier/cashier-appro.model';

@Component({
  selector: 'jhi-cashier-appro-detail',
  templateUrl: './cashier-appro-detail.component.html',
})
export class CashierApproDetailComponent implements OnInit {
  cashierAppro: ICashierAppro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierAppro }) => (this.cashierAppro = cashierAppro));
  }

  previousState(): void {
    window.history.back();
  }
}
