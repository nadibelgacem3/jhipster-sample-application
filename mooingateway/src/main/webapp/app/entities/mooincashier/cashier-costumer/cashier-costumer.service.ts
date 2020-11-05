import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICashierCostumer } from 'app/shared/model/mooincashier/cashier-costumer.model';

type EntityResponseType = HttpResponse<ICashierCostumer>;
type EntityArrayResponseType = HttpResponse<ICashierCostumer[]>;

@Injectable({ providedIn: 'root' })
export class CashierCostumerService {
  public resourceUrl = SERVER_API_URL + 'services/mooincashier/api/cashier-costumers';

  constructor(protected http: HttpClient) {}

  create(cashierCostumer: ICashierCostumer): Observable<EntityResponseType> {
    return this.http.post<ICashierCostumer>(this.resourceUrl, cashierCostumer, { observe: 'response' });
  }

  update(cashierCostumer: ICashierCostumer): Observable<EntityResponseType> {
    return this.http.put<ICashierCostumer>(this.resourceUrl, cashierCostumer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICashierCostumer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICashierCostumer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
