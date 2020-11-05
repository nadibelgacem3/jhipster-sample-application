import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITVA } from 'app/shared/model/mooincompanies/tva.model';

type EntityResponseType = HttpResponse<ITVA>;
type EntityArrayResponseType = HttpResponse<ITVA[]>;

@Injectable({ providedIn: 'root' })
export class TVAService {
  public resourceUrl = SERVER_API_URL + 'services/mooincompanies/api/tvas';

  constructor(protected http: HttpClient) {}

  create(tVA: ITVA): Observable<EntityResponseType> {
    return this.http.post<ITVA>(this.resourceUrl, tVA, { observe: 'response' });
  }

  update(tVA: ITVA): Observable<EntityResponseType> {
    return this.http.put<ITVA>(this.resourceUrl, tVA, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITVA>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITVA[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
