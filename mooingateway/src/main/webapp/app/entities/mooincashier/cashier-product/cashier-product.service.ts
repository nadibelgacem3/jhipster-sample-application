import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';

type EntityResponseType = HttpResponse<ICashierProduct>;
type EntityArrayResponseType = HttpResponse<ICashierProduct[]>;

@Injectable({ providedIn: 'root' })
export class CashierProductService {
  public resourceUrl = SERVER_API_URL + 'services/mooincashier/api/cashier-products';

  constructor(protected http: HttpClient) {}

  create(cashierProduct: ICashierProduct): Observable<EntityResponseType> {
    return this.http.post<ICashierProduct>(this.resourceUrl, cashierProduct, { observe: 'response' });
  }

  update(cashierProduct: ICashierProduct): Observable<EntityResponseType> {
    return this.http.put<ICashierProduct>(this.resourceUrl, cashierProduct, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICashierProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICashierProduct[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
