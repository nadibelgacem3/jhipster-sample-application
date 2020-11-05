import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICashierAppro } from 'app/shared/model/mooincashier/cashier-appro.model';

type EntityResponseType = HttpResponse<ICashierAppro>;
type EntityArrayResponseType = HttpResponse<ICashierAppro[]>;

@Injectable({ providedIn: 'root' })
export class CashierApproService {
  public resourceUrl = SERVER_API_URL + 'services/mooincashier/api/cashier-appros';

  constructor(protected http: HttpClient) {}

  create(cashierAppro: ICashierAppro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cashierAppro);
    return this.http
      .post<ICashierAppro>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cashierAppro: ICashierAppro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cashierAppro);
    return this.http
      .put<ICashierAppro>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICashierAppro>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICashierAppro[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cashierAppro: ICashierAppro): ICashierAppro {
    const copy: ICashierAppro = Object.assign({}, cashierAppro, {
      date: cashierAppro.date && cashierAppro.date.isValid() ? cashierAppro.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cashierAppro: ICashierAppro) => {
        cashierAppro.date = cashierAppro.date ? moment(cashierAppro.date) : undefined;
      });
    }
    return res;
  }
}
