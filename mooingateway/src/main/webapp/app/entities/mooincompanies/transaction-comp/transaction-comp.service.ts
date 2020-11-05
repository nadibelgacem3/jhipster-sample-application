import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransactionComp } from 'app/shared/model/mooincompanies/transaction-comp.model';

type EntityResponseType = HttpResponse<ITransactionComp>;
type EntityArrayResponseType = HttpResponse<ITransactionComp[]>;

@Injectable({ providedIn: 'root' })
export class TransactionCompService {
  public resourceUrl = SERVER_API_URL + 'services/mooincompanies/api/transaction-comps';

  constructor(protected http: HttpClient) {}

  create(transactionComp: ITransactionComp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionComp);
    return this.http
      .post<ITransactionComp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transactionComp: ITransactionComp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionComp);
    return this.http
      .put<ITransactionComp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransactionComp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionComp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transactionComp: ITransactionComp): ITransactionComp {
    const copy: ITransactionComp = Object.assign({}, transactionComp, {
      date: transactionComp.date && transactionComp.date.isValid() ? transactionComp.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((transactionComp: ITransactionComp) => {
        transactionComp.date = transactionComp.date ? moment(transactionComp.date) : undefined;
      });
    }
    return res;
  }
}
