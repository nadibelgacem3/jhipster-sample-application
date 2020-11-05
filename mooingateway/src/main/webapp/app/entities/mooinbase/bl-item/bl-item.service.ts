import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBLItem } from 'app/shared/model/mooinbase/bl-item.model';

type EntityResponseType = HttpResponse<IBLItem>;
type EntityArrayResponseType = HttpResponse<IBLItem[]>;

@Injectable({ providedIn: 'root' })
export class BLItemService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/bl-items';

  constructor(protected http: HttpClient) {}

  create(bLItem: IBLItem): Observable<EntityResponseType> {
    return this.http.post<IBLItem>(this.resourceUrl, bLItem, { observe: 'response' });
  }

  update(bLItem: IBLItem): Observable<EntityResponseType> {
    return this.http.put<IBLItem>(this.resourceUrl, bLItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBLItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBLItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
