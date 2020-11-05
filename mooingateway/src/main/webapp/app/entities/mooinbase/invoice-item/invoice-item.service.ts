import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInvoiceItem } from 'app/shared/model/mooinbase/invoice-item.model';

type EntityResponseType = HttpResponse<IInvoiceItem>;
type EntityArrayResponseType = HttpResponse<IInvoiceItem[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceItemService {
  public resourceUrl = SERVER_API_URL + 'services/mooinbase/api/invoice-items';

  constructor(protected http: HttpClient) {}

  create(invoiceItem: IInvoiceItem): Observable<EntityResponseType> {
    return this.http.post<IInvoiceItem>(this.resourceUrl, invoiceItem, { observe: 'response' });
  }

  update(invoiceItem: IInvoiceItem): Observable<EntityResponseType> {
    return this.http.put<IInvoiceItem>(this.resourceUrl, invoiceItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInvoiceItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInvoiceItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
