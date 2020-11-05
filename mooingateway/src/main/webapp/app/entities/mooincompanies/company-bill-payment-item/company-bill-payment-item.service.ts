import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompanyBillPaymentItem } from 'app/shared/model/mooincompanies/company-bill-payment-item.model';

type EntityResponseType = HttpResponse<ICompanyBillPaymentItem>;
type EntityArrayResponseType = HttpResponse<ICompanyBillPaymentItem[]>;

@Injectable({ providedIn: 'root' })
export class CompanyBillPaymentItemService {
  public resourceUrl = SERVER_API_URL + 'services/mooincompanies/api/company-bill-payment-items';

  constructor(protected http: HttpClient) {}

  create(companyBillPaymentItem: ICompanyBillPaymentItem): Observable<EntityResponseType> {
    return this.http.post<ICompanyBillPaymentItem>(this.resourceUrl, companyBillPaymentItem, { observe: 'response' });
  }

  update(companyBillPaymentItem: ICompanyBillPaymentItem): Observable<EntityResponseType> {
    return this.http.put<ICompanyBillPaymentItem>(this.resourceUrl, companyBillPaymentItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyBillPaymentItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyBillPaymentItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
