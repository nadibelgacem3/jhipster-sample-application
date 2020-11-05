import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaxItem } from 'app/shared/model/mooinbase/tax-item.model';

type EntityResponseType = HttpResponse<ITaxItem>;
type EntityArrayResponseType = HttpResponse<ITaxItem[]>;

@Injectable({ providedIn: 'root' })
export class TaxItemService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/tax-items';

  constructor(protected http: HttpClient) {}

  create(taxItem: ITaxItem): Observable<EntityResponseType> {
    return this.http.post<ITaxItem>(this.resourceUrl, taxItem, { observe: 'response' });
  }

  update(taxItem: ITaxItem): Observable<EntityResponseType> {
    return this.http.put<ITaxItem>(this.resourceUrl, taxItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaxItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaxItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
