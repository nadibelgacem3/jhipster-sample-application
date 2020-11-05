import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMovement } from 'app/shared/model/mooinbase/movement.model';

type EntityResponseType = HttpResponse<IMovement>;
type EntityArrayResponseType = HttpResponse<IMovement[]>;

@Injectable({ providedIn: 'root' })
export class MovementService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/movements';

  constructor(protected http: HttpClient) {}

  create(movement: IMovement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movement);
    return this.http
      .post<IMovement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(movement: IMovement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movement);
    return this.http
      .put<IMovement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMovement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMovement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(movement: IMovement): IMovement {
    const copy: IMovement = Object.assign({}, movement, {
      date: movement.date && movement.date.isValid() ? movement.date.toJSON() : undefined,
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
      res.body.forEach((movement: IMovement) => {
        movement.date = movement.date ? moment(movement.date) : undefined;
      });
    }
    return res;
  }
}
