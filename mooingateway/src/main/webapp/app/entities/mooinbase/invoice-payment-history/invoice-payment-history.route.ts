import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInvoicePaymentHistory, InvoicePaymentHistory } from 'app/shared/model/mooinbase/invoice-payment-history.model';
import { InvoicePaymentHistoryService } from './invoice-payment-history.service';
import { InvoicePaymentHistoryComponent } from './invoice-payment-history.component';
import { InvoicePaymentHistoryDetailComponent } from './invoice-payment-history-detail.component';
import { InvoicePaymentHistoryUpdateComponent } from './invoice-payment-history-update.component';

@Injectable({ providedIn: 'root' })
export class InvoicePaymentHistoryResolve implements Resolve<IInvoicePaymentHistory> {
  constructor(private service: InvoicePaymentHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvoicePaymentHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((invoicePaymentHistory: HttpResponse<InvoicePaymentHistory>) => {
          if (invoicePaymentHistory.body) {
            return of(invoicePaymentHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InvoicePaymentHistory());
  }
}

export const invoicePaymentHistoryRoute: Routes = [
  {
    path: '',
    component: InvoicePaymentHistoryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinbaseInvoicePaymentHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InvoicePaymentHistoryDetailComponent,
    resolve: {
      invoicePaymentHistory: InvoicePaymentHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseInvoicePaymentHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InvoicePaymentHistoryUpdateComponent,
    resolve: {
      invoicePaymentHistory: InvoicePaymentHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseInvoicePaymentHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InvoicePaymentHistoryUpdateComponent,
    resolve: {
      invoicePaymentHistory: InvoicePaymentHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseInvoicePaymentHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
