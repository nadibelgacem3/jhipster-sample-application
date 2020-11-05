import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompanyLocation, CompanyLocation } from 'app/shared/model/mooincompanies/company-location.model';
import { CompanyLocationService } from './company-location.service';
import { CompanyLocationComponent } from './company-location.component';
import { CompanyLocationDetailComponent } from './company-location-detail.component';
import { CompanyLocationUpdateComponent } from './company-location-update.component';

@Injectable({ providedIn: 'root' })
export class CompanyLocationResolve implements Resolve<ICompanyLocation> {
  constructor(private service: CompanyLocationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyLocation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((companyLocation: HttpResponse<CompanyLocation>) => {
          if (companyLocation.body) {
            return of(companyLocation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompanyLocation());
  }
}

export const companyLocationRoute: Routes = [
  {
    path: '',
    component: CompanyLocationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyLocationDetailComponent,
    resolve: {
      companyLocation: CompanyLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyLocationUpdateComponent,
    resolve: {
      companyLocation: CompanyLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyLocationUpdateComponent,
    resolve: {
      companyLocation: CompanyLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
