import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICashierReceiptPay } from 'app/shared/model/mooincashier/cashier-receipt-pay.model';

type EntityResponseType = HttpResponse<ICashierReceiptPay>;
type EntityArrayResponseType = HttpResponse<ICashierReceiptPay[]>;

@Injectable({ providedIn: 'root' })
export class CashierReceiptPayService {
  public resourceUrl = SERVER_API_URL + 'services/mooincashier/api/cashier-receipt-pays';

  constructor(protected http: HttpClient) {}

  create(cashierReceiptPay: ICashierReceiptPay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cashierReceiptPay);
    return this.http
      .post<ICashierReceiptPay>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cashierReceiptPay: ICashierReceiptPay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cashierReceiptPay);
    return this.http
      .put<ICashierReceiptPay>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICashierReceiptPay>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICashierReceiptPay[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cashierReceiptPay: ICashierReceiptPay): ICashierReceiptPay {
    const copy: ICashierReceiptPay = Object.assign({}, cashierReceiptPay, {
      paymentDate:
        cashierReceiptPay.paymentDate && cashierReceiptPay.paymentDate.isValid() ? cashierReceiptPay.paymentDate.toJSON() : undefined,
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
      res.body.forEach((cashierReceiptPay: ICashierReceiptPay) => {
        cashierReceiptPay.paymentDate = cashierReceiptPay.paymentDate ? moment(cashierReceiptPay.paymentDate) : undefined;
      });
    }
    return res;
  }
}
