import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICashierLocation } from 'app/shared/model/mooincashier/cashier-location.model';

type EntityResponseType = HttpResponse<ICashierLocation>;
type EntityArrayResponseType = HttpResponse<ICashierLocation[]>;

@Injectable({ providedIn: 'root' })
export class CashierLocationService {
  public resourceUrl = SERVER_API_URL + 'services/mooincashier/api/cashier-locations';

  constructor(protected http: HttpClient) {}

  create(cashierLocation: ICashierLocation): Observable<EntityResponseType> {
    return this.http.post<ICashierLocation>(this.resourceUrl, cashierLocation, { observe: 'response' });
  }

  update(cashierLocation: ICashierLocation): Observable<EntityResponseType> {
    return this.http.put<ICashierLocation>(this.resourceUrl, cashierLocation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICashierLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICashierLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
