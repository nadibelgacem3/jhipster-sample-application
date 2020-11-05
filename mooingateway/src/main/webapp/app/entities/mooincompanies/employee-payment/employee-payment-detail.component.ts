import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmployeePayment } from 'app/shared/model/mooincompanies/employee-payment.model';

@Component({
  selector: 'jhi-employee-payment-detail',
  templateUrl: './employee-payment-detail.component.html',
})
export class EmployeePaymentDetailComponent implements OnInit {
  employeePayment: IEmployeePayment | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeePayment }) => (this.employeePayment = employeePayment));
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
