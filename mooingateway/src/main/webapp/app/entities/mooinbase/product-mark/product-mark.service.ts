import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductMark } from 'app/shared/model/mooinbase/product-mark.model';

type EntityResponseType = HttpResponse<IProductMark>;
type EntityArrayResponseType = HttpResponse<IProductMark[]>;

@Injectable({ providedIn: 'root' })
export class ProductMarkService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/product-marks';

  constructor(protected http: HttpClient) {}

  create(productMark: IProductMark): Observable<EntityResponseType> {
    return this.http.post<IProductMark>(this.resourceUrl, productMark, { observe: 'response' });
  }

  update(productMark: IProductMark): Observable<EntityResponseType> {
    return this.http.put<IProductMark>(this.resourceUrl, productMark, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductMark>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductMark[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
