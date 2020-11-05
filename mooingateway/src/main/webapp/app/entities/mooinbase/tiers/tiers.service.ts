import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITiers } from 'app/shared/model/mooinbase/tiers.model';

type EntityResponseType = HttpResponse<ITiers>;
type EntityArrayResponseType = HttpResponse<ITiers[]>;

@Injectable({ providedIn: 'root' })
export class TiersService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/tiers';

  constructor(protected http: HttpClient) {}

  create(tiers: ITiers): Observable<EntityResponseType> {
    return this.http.post<ITiers>(this.resourceUrl, tiers, { observe: 'response' });
  }

  update(tiers: ITiers): Observable<EntityResponseType> {
    return this.http.put<ITiers>(this.resourceUrl, tiers, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITiers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITiers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
