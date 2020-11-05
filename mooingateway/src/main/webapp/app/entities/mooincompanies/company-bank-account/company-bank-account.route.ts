import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompanyBankAccount, CompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';
import { CompanyBankAccountService } from './company-bank-account.service';
import { CompanyBankAccountComponent } from './company-bank-account.component';
import { CompanyBankAccountDetailComponent } from './company-bank-account-detail.component';
import { CompanyBankAccountUpdateComponent } from './company-bank-account-update.component';

@Injectable({ providedIn: 'root' })
export class CompanyBankAccountResolve implements Resolve<ICompanyBankAccount> {
  constructor(private service: CompanyBankAccountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyBankAccount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((companyBankAccount: HttpResponse<CompanyBankAccount>) => {
          if (companyBankAccount.body) {
            return of(companyBankAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompanyBankAccount());
  }
}

export const companyBankAccountRoute: Routes = [
  {
    path: '',
    component: CompanyBankAccountComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBankAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyBankAccountDetailComponent,
    resolve: {
      companyBankAccount: CompanyBankAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBankAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyBankAccountUpdateComponent,
    resolve: {
      companyBankAccount: CompanyBankAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBankAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyBankAccountUpdateComponent,
    resolve: {
      companyBankAccount: CompanyBankAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBankAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
