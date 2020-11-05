import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICashierApproItem } from 'app/shared/model/mooincashier/cashier-appro-item.model';

type EntityResponseType = HttpResponse<ICashierApproItem>;
type EntityArrayResponseType = HttpResponse<ICashierApproItem[]>;

@Injectable({ providedIn: 'root' })
export class CashierApproItemService {
  public resourceUrl = SERVER_API_URL + 'services/mooincashier/api/cashier-appro-items';

  constructor(protected http: HttpClient) {}

  create(cashierApproItem: ICashierApproItem): Observable<EntityResponseType> {
    return this.http.post<ICashierApproItem>(this.resourceUrl, cashierApproItem, { observe: 'response' });
  }

  update(cashierApproItem: ICashierApproItem): Observable<EntityResponseType> {
    return this.http.put<ICashierApproItem>(this.resourceUrl, cashierApproItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICashierApproItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICashierApproItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
