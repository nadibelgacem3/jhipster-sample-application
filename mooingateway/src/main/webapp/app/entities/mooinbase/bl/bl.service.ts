import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBL } from 'app/shared/model/mooinbase/bl.model';

type EntityResponseType = HttpResponse<IBL>;
type EntityArrayResponseType = HttpResponse<IBL[]>;

@Injectable({ providedIn: 'root' })
export class BLService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/bls';

  constructor(protected http: HttpClient) {}

  create(bL: IBL): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bL);
    return this.http
      .post<IBL>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bL: IBL): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bL);
    return this.http
      .put<IBL>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBL>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBL[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bL: IBL): IBL {
    const copy: IBL = Object.assign({}, bL, {
      date: bL.date && bL.date.isValid() ? bL.date.format(DATE_FORMAT) : undefined,
      dueDate: bL.dueDate && bL.dueDate.isValid() ? bL.dueDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((bL: IBL) => {
        bL.date = bL.date ? moment(bL.date) : undefined;
        bL.dueDate = bL.dueDate ? moment(bL.dueDate) : undefined;
      });
    }
    return res;
  }
}
