import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuoteItem } from 'app/shared/model/mooinbase/quote-item.model';

type EntityResponseType = HttpResponse<IQuoteItem>;
type EntityArrayResponseType = HttpResponse<IQuoteItem[]>;

@Injectable({ providedIn: 'root' })
export class QuoteItemService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/quote-items';

  constructor(protected http: HttpClient) {}

  create(quoteItem: IQuoteItem): Observable<EntityResponseType> {
    return this.http.post<IQuoteItem>(this.resourceUrl, quoteItem, { observe: 'response' });
  }

  update(quoteItem: IQuoteItem): Observable<EntityResponseType> {
    return this.http.put<IQuoteItem>(this.resourceUrl, quoteItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuoteItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuoteItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
