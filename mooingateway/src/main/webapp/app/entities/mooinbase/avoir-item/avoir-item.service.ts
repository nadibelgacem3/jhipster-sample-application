import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvoirItem } from 'app/shared/model/mooinbase/avoir-item.model';

type EntityResponseType = HttpResponse<IAvoirItem>;
type EntityArrayResponseType = HttpResponse<IAvoirItem[]>;

@Injectable({ providedIn: 'root' })
export class AvoirItemService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/avoir-items';

  constructor(protected http: HttpClient) {}

  create(avoirItem: IAvoirItem): Observable<EntityResponseType> {
    return this.http.post<IAvoirItem>(this.resourceUrl, avoirItem, { observe: 'response' });
  }

  update(avoirItem: IAvoirItem): Observable<EntityResponseType> {
    return this.http.put<IAvoirItem>(this.resourceUrl, avoirItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvoirItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvoirItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
