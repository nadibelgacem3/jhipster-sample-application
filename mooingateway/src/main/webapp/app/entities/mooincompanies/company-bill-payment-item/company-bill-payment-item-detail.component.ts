import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyBillPaymentItem } from 'app/shared/model/mooincompanies/company-bill-payment-item.model';

@Component({
  selector: 'jhi-company-bill-payment-item-detail',
  templateUrl: './company-bill-payment-item-detail.component.html',
})
export class CompanyBillPaymentItemDetailComponent implements OnInit {
  companyBillPaymentItem: ICompanyBillPaymentItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyBillPaymentItem }) => (this.companyBillPaymentItem = companyBillPaymentItem));
  }

  previousState(): void {
    window.history.back();
  }
}
