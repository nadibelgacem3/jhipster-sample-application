import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeeLocation } from 'app/shared/model/mooincompanies/employee-location.model';

type EntityResponseType = HttpResponse<IEmployeeLocation>;
type EntityArrayResponseType = HttpResponse<IEmployeeLocation[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeLocationService {
  public resourceUrl = SERVER_API_URL + 'services/mooincompanies/api/employee-locations';

  constructor(protected http: HttpClient) {}

  create(employeeLocation: IEmployeeLocation): Observable<EntityResponseType> {
    return this.http.post<IEmployeeLocation>(this.resourceUrl, employeeLocation, { observe: 'response' });
  }

  update(employeeLocation: IEmployeeLocation): Observable<EntityResponseType> {
    return this.http.put<IEmployeeLocation>(this.resourceUrl, employeeLocation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmployeeLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmployeeLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
