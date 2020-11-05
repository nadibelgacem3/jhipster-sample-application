import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompanyModule } from 'app/shared/model/mooincompanies/company-module.model';

type EntityResponseType = HttpResponse<ICompanyModule>;
type EntityArrayResponseType = HttpResponse<ICompanyModule[]>;

@Injectable({ providedIn: 'root' })
export class CompanyModuleService {
  public resourceUrl = SERVER_API_URL + 'services/mooincompanies/api/company-modules';

  constructor(protected http: HttpClient) {}

  create(companyModule: ICompanyModule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(companyModule);
    return this.http
      .post<ICompanyModule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(companyModule: ICompanyModule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(companyModule);
    return this.http
      .put<ICompanyModule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompanyModule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompanyModule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(companyModule: ICompanyModule): ICompanyModule {
    const copy: ICompanyModule = Object.assign({}, companyModule, {
      activatedAt:
        companyModule.activatedAt && companyModule.activatedAt.isValid() ? companyModule.activatedAt.format(DATE_FORMAT) : undefined,
      activatedUntil:
        companyModule.activatedUntil && companyModule.activatedUntil.isValid()
          ? companyModule.activatedUntil.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.activatedAt = res.body.activatedAt ? moment(res.body.activatedAt) : undefined;
      res.body.activatedUntil = res.body.activatedUntil ? moment(res.body.activatedUntil) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((companyModule: ICompanyModule) => {
        companyModule.activatedAt = companyModule.activatedAt ? moment(companyModule.activatedAt) : undefined;
        companyModule.activatedUntil = companyModule.activatedUntil ? moment(companyModule.activatedUntil) : undefined;
      });
    }
    return res;
  }
}
