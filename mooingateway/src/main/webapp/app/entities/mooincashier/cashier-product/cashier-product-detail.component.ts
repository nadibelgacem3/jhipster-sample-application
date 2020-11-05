import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';

@Component({
  selector: 'jhi-cashier-product-detail',
  templateUrl: './cashier-product-detail.component.html',
})
export class CashierProductDetailComponent implements OnInit {
  cashierProduct: ICashierProduct | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierProduct }) => (this.cashierProduct = cashierProduct));
  }

  previousState(): void {
    window.history.back();
  }
}
