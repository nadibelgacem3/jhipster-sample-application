import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeePayment } from 'app/shared/model/mooincompanies/employee-payment.model';

type EntityResponseType = HttpResponse<IEmployeePayment>;
type EntityArrayResponseType = HttpResponse<IEmployeePayment[]>;

@Injectable({ providedIn: 'root' })
export class EmployeePaymentService {
  public resourceUrl = SERVER_API_URL + 'services/mooincompanies/api/employee-payments';

  constructor(protected http: HttpClient) {}

  create(employeePayment: IEmployeePayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeePayment);
    return this.http
      .post<IEmployeePayment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employeePayment: IEmployeePayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeePayment);
    return this.http
      .put<IEmployeePayment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmployeePayment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmployeePayment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(employeePayment: IEmployeePayment): IEmployeePayment {
    const copy: IEmployeePayment = Object.assign({}, employeePayment, {
      paymentDate: employeePayment.paymentDate && employeePayment.paymentDate.isValid() ? employeePayment.paymentDate.toJSON() : undefined,
      fromDate: employeePayment.fromDate && employeePayment.fromDate.isValid() ? employeePayment.fromDate.format(DATE_FORMAT) : undefined,
      toDate: employeePayment.toDate && employeePayment.toDate.isValid() ? employeePayment.toDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.paymentDate = res.body.paymentDate ? moment(res.body.paymentDate) : undefined;
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.toDate = res.body.toDate ? moment(res.body.toDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employeePayment: IEmployeePayment) => {
        employeePayment.paymentDate = employeePayment.paymentDate ? moment(employeePayment.paymentDate) : undefined;
        employeePayment.fromDate = employeePayment.fromDate ? moment(employeePayment.fromDate) : undefined;
        employeePayment.toDate = employeePayment.toDate ? moment(employeePayment.toDate) : undefined;
      });
    }
    return res;
  }
}
