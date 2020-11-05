import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITiersLocation } from 'app/shared/model/mooinbase/tiers-location.model';

type EntityResponseType = HttpResponse<ITiersLocation>;
type EntityArrayResponseType = HttpResponse<ITiersLocation[]>;

@Injectable({ providedIn: 'root' })
export class TiersLocationService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/tiers-locations';

  constructor(protected http: HttpClient) {}

  create(tiersLocation: ITiersLocation): Observable<EntityResponseType> {
    return this.http.post<ITiersLocation>(this.resourceUrl, tiersLocation, { observe: 'response' });
  }

  update(tiersLocation: ITiersLocation): Observable<EntityResponseType> {
    return this.http.put<ITiersLocation>(this.resourceUrl, tiersLocation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITiersLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITiersLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
