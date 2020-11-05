import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICashierReceiptItem } from 'app/shared/model/mooincashier/cashier-receipt-item.model';

type EntityResponseType = HttpResponse<ICashierReceiptItem>;
type EntityArrayResponseType = HttpResponse<ICashierReceiptItem[]>;

@Injectable({ providedIn: 'root' })
export class CashierReceiptItemService {
  public resourceUrl = SERVER_API_URL + 'services/mooincashier/api/cashier-receipt-items';

  constructor(protected http: HttpClient) {}

  create(cashierReceiptItem: ICashierReceiptItem): Observable<EntityResponseType> {
    return this.http.post<ICashierReceiptItem>(this.resourceUrl, cashierReceiptItem, { observe: 'response' });
  }

  update(cashierReceiptItem: ICashierReceiptItem): Observable<EntityResponseType> {
    return this.http.put<ICashierReceiptItem>(this.resourceUrl, cashierReceiptItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICashierReceiptItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICashierReceiptItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
