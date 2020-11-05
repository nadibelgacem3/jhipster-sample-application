import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICashierLocation } from 'app/shared/model/mooincashier/cashier-location.model';

@Component({
  selector: 'jhi-cashier-location-detail',
  templateUrl: './cashier-location-detail.component.html',
})
export class CashierLocationDetailComponent implements OnInit {
  cashierLocation: ICashierLocation | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierLocation }) => (this.cashierLocation = cashierLocation));
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
