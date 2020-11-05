import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompanyBillPaymentItem, CompanyBillPaymentItem } from 'app/shared/model/mooincompanies/company-bill-payment-item.model';
import { CompanyBillPaymentItemService } from './company-bill-payment-item.service';
import { CompanyBillPaymentItemComponent } from './company-bill-payment-item.component';
import { CompanyBillPaymentItemDetailComponent } from './company-bill-payment-item-detail.component';
import { CompanyBillPaymentItemUpdateComponent } from './company-bill-payment-item-update.component';

@Injectable({ providedIn: 'root' })
export class CompanyBillPaymentItemResolve implements Resolve<ICompanyBillPaymentItem> {
  constructor(private service: CompanyBillPaymentItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyBillPaymentItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((companyBillPaymentItem: HttpResponse<CompanyBillPaymentItem>) => {
          if (companyBillPaymentItem.body) {
            return of(companyBillPaymentItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompanyBillPaymentItem());
  }
}

export const companyBillPaymentItemRoute: Routes = [
  {
    path: '',
    component: CompanyBillPaymentItemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBillPaymentItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyBillPaymentItemDetailComponent,
    resolve: {
      companyBillPaymentItem: CompanyBillPaymentItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBillPaymentItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyBillPaymentItemUpdateComponent,
    resolve: {
      companyBillPaymentItem: CompanyBillPaymentItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBillPaymentItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyBillPaymentItemUpdateComponent,
    resolve: {
      companyBillPaymentItem: CompanyBillPaymentItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesCompanyBillPaymentItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
