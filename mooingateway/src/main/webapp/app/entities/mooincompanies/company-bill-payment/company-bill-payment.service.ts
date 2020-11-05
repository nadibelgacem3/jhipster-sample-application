import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompanyBillPayment } from 'app/shared/model/mooincompanies/company-bill-payment.model';

type EntityResponseType = HttpResponse<ICompanyBillPayment>;
type EntityArrayResponseType = HttpResponse<ICompanyBillPayment[]>;

@Injectable({ providedIn: 'root' })
export class CompanyBillPaymentService {
  public resourceUrl = SERVER_API_URL + 'services/mooincompanies/api/company-bill-payments';

  constructor(protected http: HttpClient) {}

  create(companyBillPayment: ICompanyBillPayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(companyBillPayment);
    return this.http
      .post<ICompanyBillPayment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(companyBillPayment: ICompanyBillPayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(companyBillPayment);
    return this.http
      .put<ICompanyBillPayment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompanyBillPayment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompanyBillPayment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(companyBillPayment: ICompanyBillPayment): ICompanyBillPayment {
    const copy: ICompanyBillPayment = Object.assign({}, companyBillPayment, {
      paymentDate:
        companyBillPayment.paymentDate && companyBillPayment.paymentDate.isValid()
          ? companyBillPayment.paymentDate.format(DATE_FORMAT)
          : undefined,
      date: companyBillPayment.date && companyBillPayment.date.isValid() ? companyBillPayment.date.format(DATE_FORMAT) : undefined,
      dueDate:
        companyBillPayment.dueDate && companyBillPayment.dueDate.isValid() ? companyBillPayment.dueDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.paymentDate = res.body.paymentDate ? moment(res.body.paymentDate) : undefined;
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.dueDate = res.body.dueDate ? moment(res.body.dueDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((companyBillPayment: ICompanyBillPayment) => {
        companyBillPayment.paymentDate = companyBillPayment.paymentDate ? moment(companyBillPayment.paymentDate) : undefined;
        companyBillPayment.date = companyBillPayment.date ? moment(companyBillPayment.date) : undefined;
        companyBillPayment.dueDate = companyBillPayment.dueDate ? moment(companyBillPayment.dueDate) : undefined;
      });
    }
    return res;
  }
}
