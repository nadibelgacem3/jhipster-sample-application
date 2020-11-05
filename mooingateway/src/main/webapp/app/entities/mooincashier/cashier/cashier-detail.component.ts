import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICashier } from 'app/shared/model/mooincashier/cashier.model';

@Component({
  selector: 'jhi-cashier-detail',
  templateUrl: './cashier-detail.component.html',
})
export class CashierDetailComponent implements OnInit {
  cashier: ICashier | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashier }) => (this.cashier = cashier));
  }

  previousState(): void {
    window.history.back();
  }
}
