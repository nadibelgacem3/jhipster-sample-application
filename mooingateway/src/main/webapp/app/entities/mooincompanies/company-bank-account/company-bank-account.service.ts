import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';

type EntityResponseType = HttpResponse<ICompanyBankAccount>;
type EntityArrayResponseType = HttpResponse<ICompanyBankAccount[]>;

@Injectable({ providedIn: 'root' })
export class CompanyBankAccountService {
  public resourceUrl = SERVER_API_URL + 'services/mooincompanies/api/company-bank-accounts';

  constructor(protected http: HttpClient) {}

  create(companyBankAccount: ICompanyBankAccount): Observable<EntityResponseType> {
    return this.http.post<ICompanyBankAccount>(this.resourceUrl, companyBankAccount, { observe: 'response' });
  }

  update(companyBankAccount: ICompanyBankAccount): Observable<EntityResponseType> {
    return this.http.put<ICompanyBankAccount>(this.resourceUrl, companyBankAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyBankAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyBankAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
