import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';

type EntityResponseType = HttpResponse<ICashierReceipt>;
type EntityArrayResponseType = HttpResponse<ICashierReceipt[]>;

@Injectable({ providedIn: 'root' })
export class CashierReceiptService {
  public resourceUrl = SERVER_API_URL + 'services/mooincashier/api/cashier-receipts';

  constructor(protected http: HttpClient) {}

  create(cashierReceipt: ICashierReceipt): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cashierReceipt);
    return this.http
      .post<ICashierReceipt>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cashierReceipt: ICashierReceipt): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cashierReceipt);
    return this.http
      .put<ICashierReceipt>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICashierReceipt>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICashierReceipt[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cashierReceipt: ICashierReceipt): ICashierReceipt {
    const copy: ICashierReceipt = Object.assign({}, cashierReceipt, {
      date: cashierReceipt.date && cashierReceipt.date.isValid() ? cashierReceipt.date.format(DATE_FORMAT) : undefined,
      dueDate: cashierReceipt.dueDate && cashierReceipt.dueDate.isValid() ? cashierReceipt.dueDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.dueDate = res.body.dueDate ? moment(res.body.dueDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cashierReceipt: ICashierReceipt) => {
        cashierReceipt.date = cashierReceipt.date ? moment(cashierReceipt.date) : undefined;
        cashierReceipt.dueDate = cashierReceipt.dueDate ? moment(cashierReceipt.dueDate) : undefined;
      });
    }
    return res;
  }
}
