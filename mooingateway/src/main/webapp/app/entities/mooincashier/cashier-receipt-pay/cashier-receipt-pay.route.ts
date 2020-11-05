import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICashierReceiptPay, CashierReceiptPay } from 'app/shared/model/mooincashier/cashier-receipt-pay.model';
import { CashierReceiptPayService } from './cashier-receipt-pay.service';
import { CashierReceiptPayComponent } from './cashier-receipt-pay.component';
import { CashierReceiptPayDetailComponent } from './cashier-receipt-pay-detail.component';
import { CashierReceiptPayUpdateComponent } from './cashier-receipt-pay-update.component';

@Injectable({ providedIn: 'root' })
export class CashierReceiptPayResolve implements Resolve<ICashierReceiptPay> {
  constructor(private service: CashierReceiptPayService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICashierReceiptPay> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cashierReceiptPay: HttpResponse<CashierReceiptPay>) => {
          if (cashierReceiptPay.body) {
            return of(cashierReceiptPay.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CashierReceiptPay());
  }
}

export const cashierReceiptPayRoute: Routes = [
  {
    path: '',
    component: CashierReceiptPayComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincashierCashierReceiptPay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CashierReceiptPayDetailComponent,
    resolve: {
      cashierReceiptPay: CashierReceiptPayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierReceiptPay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CashierReceiptPayUpdateComponent,
    resolve: {
      cashierReceiptPay: CashierReceiptPayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierReceiptPay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CashierReceiptPayUpdateComponent,
    resolve: {
      cashierReceiptPay: CashierReceiptPayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierReceiptPay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
