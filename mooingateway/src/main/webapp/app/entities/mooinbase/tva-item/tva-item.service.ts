import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITVAItem } from 'app/shared/model/mooinbase/tva-item.model';

type EntityResponseType = HttpResponse<ITVAItem>;
type EntityArrayResponseType = HttpResponse<ITVAItem[]>;

@Injectable({ providedIn: 'root' })
export class TVAItemService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/tva-items';

  constructor(protected http: HttpClient) {}

  create(tVAItem: ITVAItem): Observable<EntityResponseType> {
    return this.http.post<ITVAItem>(this.resourceUrl, tVAItem, { observe: 'response' });
  }

  update(tVAItem: ITVAItem): Observable<EntityResponseType> {
    return this.http.put<ITVAItem>(this.resourceUrl, tVAItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITVAItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITVAItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
