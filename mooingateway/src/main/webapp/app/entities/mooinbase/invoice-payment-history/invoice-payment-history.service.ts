import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInvoicePaymentHistory } from 'app/shared/model/mooinbase/invoice-payment-history.model';

type EntityResponseType = HttpResponse<IInvoicePaymentHistory>;
type EntityArrayResponseType = HttpResponse<IInvoicePaymentHistory[]>;

@Injectable({ providedIn: 'root' })
export class InvoicePaymentHistoryService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/invoice-payment-histories';

  constructor(protected http: HttpClient) {}

  create(invoicePaymentHistory: IInvoicePaymentHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoicePaymentHistory);
    return this.http
      .post<IInvoicePaymentHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invoicePaymentHistory: IInvoicePaymentHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoicePaymentHistory);
    return this.http
      .put<IInvoicePaymentHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInvoicePaymentHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoicePaymentHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(invoicePaymentHistory: IInvoicePaymentHistory): IInvoicePaymentHistory {
    const copy: IInvoicePaymentHistory = Object.assign({}, invoicePaymentHistory, {
      paymentDate:
        invoicePaymentHistory.paymentDate && invoicePaymentHistory.paymentDate.isValid()
          ? invoicePaymentHistory.paymentDate.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.paymentDate = res.body.paymentDate ? moment(res.body.paymentDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((invoicePaymentHistory: IInvoicePaymentHistory) => {
        invoicePaymentHistory.paymentDate = invoicePaymentHistory.paymentDate ? moment(invoicePaymentHistory.paymentDate) : undefined;
      });
    }
    return res;
  }
}
