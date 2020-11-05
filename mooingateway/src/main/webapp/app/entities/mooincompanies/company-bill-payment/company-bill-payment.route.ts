import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompanyBillPayment, CompanyBillPayment } from 'app/shared/model/mooincompanies/company-bill-payment.model';
import { CompanyBillPaymentService } from './company-bill-payment.service';
import { CompanyBillPaymentComponent } from './company-bill-payment.component';
import { CompanyBillPaymentDetailComponent } from './company-bill-payment-detail.component';
import { CompanyBillPaymentUpdateComponent } from './company-bill-payment-update.component';

@Injectable({ providedIn: 'root' })
export class CompanyBillPaymentResolve implements Resolve<ICompanyBillPayment> {
  constructor(private service: CompanyBillPaymentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyBillPayment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((companyBillPayment: HttpResponse<CompanyBillPayment>) => {
          if (companyBillPayment.body) {
            return of(companyBillPayment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompanyBillPayment());
  }
}

export const companyBillPaymentRoute: Routes = [
  {
    path: '',
    component: CompanyBillPaymentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBillPayment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyBillPaymentDetailComponent,
    resolve: {
      companyBillPayment: CompanyBillPaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBillPayment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyBillPaymentUpdateComponent,
    resolve: {
      companyBillPayment: CompanyBillPaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBillPayment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyBillPaymentUpdateComponent,
    resolve: {
      companyBillPayment: CompanyBillPaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBillPayment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
