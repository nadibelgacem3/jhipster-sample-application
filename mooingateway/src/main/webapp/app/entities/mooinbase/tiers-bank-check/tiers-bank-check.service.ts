import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITiersBankCheck } from 'app/shared/model/mooinbase/tiers-bank-check.model';

type EntityResponseType = HttpResponse<ITiersBankCheck>;
type EntityArrayResponseType = HttpResponse<ITiersBankCheck[]>;

@Injectable({ providedIn: 'root' })
export class TiersBankCheckService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/tiers-bank-checks';

  constructor(protected http: HttpClient) {}

  create(tiersBankCheck: ITiersBankCheck): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tiersBankCheck);
    return this.http
      .post<ITiersBankCheck>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tiersBankCheck: ITiersBankCheck): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tiersBankCheck);
    return this.http
      .put<ITiersBankCheck>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITiersBankCheck>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITiersBankCheck[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tiersBankCheck: ITiersBankCheck): ITiersBankCheck {
    const copy: ITiersBankCheck = Object.assign({}, tiersBankCheck, {
      dueDate: tiersBankCheck.dueDate && tiersBankCheck.dueDate.isValid() ? tiersBankCheck.dueDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dueDate = res.body.dueDate ? moment(res.body.dueDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tiersBankCheck: ITiersBankCheck) => {
        tiersBankCheck.dueDate = tiersBankCheck.dueDate ? moment(tiersBankCheck.dueDate) : undefined;
      });
    }
    return res;
  }
}
