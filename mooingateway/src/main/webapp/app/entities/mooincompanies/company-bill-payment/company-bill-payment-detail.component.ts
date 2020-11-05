import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICompanyBillPayment } from 'app/shared/model/mooincompanies/company-bill-payment.model';

@Component({
  selector: 'jhi-company-bill-payment-detail',
  templateUrl: './company-bill-payment-detail.component.html',
})
export class CompanyBillPaymentDetailComponent implements OnInit {
  companyBillPayment: ICompanyBillPayment | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyBillPayment }) => (this.companyBillPayment = companyBillPayment));
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
