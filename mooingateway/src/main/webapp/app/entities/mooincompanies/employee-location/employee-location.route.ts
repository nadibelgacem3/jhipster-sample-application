import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployeeLocation, EmployeeLocation } from 'app/shared/model/mooincompanies/employee-location.model';
import { EmployeeLocationService } from './employee-location.service';
import { EmployeeLocationComponent } from './employee-location.component';
import { EmployeeLocationDetailComponent } from './employee-location-detail.component';
import { EmployeeLocationUpdateComponent } from './employee-location-update.component';

@Injectable({ providedIn: 'root' })
export class EmployeeLocationResolve implements Resolve<IEmployeeLocation> {
  constructor(private service: EmployeeLocationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeeLocation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employeeLocation: HttpResponse<EmployeeLocation>) => {
          if (employeeLocation.body) {
            return of(employeeLocation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmployeeLocation());
  }
}

export const employeeLocationRoute: Routes = [
  {
    path: '',
    component: EmployeeLocationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincompaniesEmployeeLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeLocationDetailComponent,
    resolve: {
      employeeLocation: EmployeeLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesEmployeeLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeLocationUpdateComponent,
    resolve: {
      employeeLocation: EmployeeLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesEmployeeLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeLocationUpdateComponent,
    resolve: {
      employeeLocation: EmployeeLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesEmployeeLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
