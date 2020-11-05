import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompanyModule, CompanyModule } from 'app/shared/model/mooincompanies/company-module.model';
import { CompanyModuleService } from './company-module.service';
import { CompanyModuleComponent } from './company-module.component';
import { CompanyModuleDetailComponent } from './company-module-detail.component';
import { CompanyModuleUpdateComponent } from './company-module-update.component';

@Injectable({ providedIn: 'root' })
export class CompanyModuleResolve implements Resolve<ICompanyModule> {
  constructor(private service: CompanyModuleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyModule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((companyModule: HttpResponse<CompanyModule>) => {
          if (companyModule.body) {
            return of(companyModule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompanyModule());
  }
}

export const companyModuleRoute: Routes = [
  {
    path: '',
    component: CompanyModuleComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyModule.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyModuleDetailComponent,
    resolve: {
      companyModule: CompanyModuleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyModule.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyModuleUpdateComponent,
    resolve: {
      companyModule: CompanyModuleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyModule.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyModuleUpdateComponent,
    resolve: {
      companyModule: CompanyModuleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyModule.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
