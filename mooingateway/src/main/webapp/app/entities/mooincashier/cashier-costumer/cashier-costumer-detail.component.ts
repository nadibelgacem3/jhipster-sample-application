import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICashierCostumer } from 'app/shared/model/mooincashier/cashier-costumer.model';

@Component({
  selector: 'jhi-cashier-costumer-detail',
  templateUrl: './cashier-costumer-detail.component.html',
})
export class CashierCostumerDetailComponent implements OnInit {
  cashierCostumer: ICashierCostumer | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierCostumer }) => (this.cashierCostumer = cashierCostumer));
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
