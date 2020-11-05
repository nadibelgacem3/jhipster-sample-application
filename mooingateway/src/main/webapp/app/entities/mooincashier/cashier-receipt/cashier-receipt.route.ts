import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICashierReceipt, CashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';
import { CashierReceiptService } from './cashier-receipt.service';
import { CashierReceiptComponent } from './cashier-receipt.component';
import { CashierReceiptDetailComponent } from './cashier-receipt-detail.component';
import { CashierReceiptUpdateComponent } from './cashier-receipt-update.component';

@Injectable({ providedIn: 'root' })
export class CashierReceiptResolve implements Resolve<ICashierReceipt> {
  constructor(private service: CashierReceiptService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICashierReceipt> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cashierReceipt: HttpResponse<CashierReceipt>) => {
          if (cashierReceipt.body) {
            return of(cashierReceipt.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CashierReceipt());
  }
}

export const cashierReceiptRoute: Routes = [
  {
    path: '',
    component: CashierReceiptComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincashierCashierReceipt.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CashierReceiptDetailComponent,
    resolve: {
      cashierReceipt: CashierReceiptResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierReceipt.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CashierReceiptUpdateComponent,
    resolve: {
      cashierReceipt: CashierReceiptResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierReceipt.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CashierReceiptUpdateComponent,
    resolve: {
      cashierReceipt: CashierReceiptResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierReceipt.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
