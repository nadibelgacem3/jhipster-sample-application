import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOfferOrder } from 'app/shared/model/mooinecommerce/offer-order.model';

type EntityResponseType = HttpResponse<IOfferOrder>;
type EntityArrayResponseType = HttpResponse<IOfferOrder[]>;

@Injectable({ providedIn: 'root' })
export class OfferOrderService {
  public resourceUrl = SERVER_API_URL + 'services/mooinecommerce/api/offer-orders';

  constructor(protected http: HttpClient) {}

  create(offerOrder: IOfferOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offerOrder);
    return this.http
      .post<IOfferOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(offerOrder: IOfferOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offerOrder);
    return this.http
      .put<IOfferOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOfferOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOfferOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(offerOrder: IOfferOrder): IOfferOrder {
    const copy: IOfferOrder = Object.assign({}, offerOrder, {
      date: offerOrder.date && offerOrder.date.isValid() ? offerOrder.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((offerOrder: IOfferOrder) => {
        offerOrder.date = offerOrder.date ? moment(offerOrder.date) : undefined;
      });
    }
    return res;
  }
}
