import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICashierReceiptItem, CashierReceiptItem } from 'app/shared/model/mooincashier/cashier-receipt-item.model';
import { CashierReceiptItemService } from './cashier-receipt-item.service';
import { CashierReceiptItemComponent } from './cashier-receipt-item.component';
import { CashierReceiptItemDetailComponent } from './cashier-receipt-item-detail.component';
import { CashierReceiptItemUpdateComponent } from './cashier-receipt-item-update.component';

@Injectable({ providedIn: 'root' })
export class CashierReceiptItemResolve implements Resolve<ICashierReceiptItem> {
  constructor(private service: CashierReceiptItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICashierReceiptItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cashierReceiptItem: HttpResponse<CashierReceiptItem>) => {
          if (cashierReceiptItem.body) {
            return of(cashierReceiptItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CashierReceiptItem());
  }
}

export const cashierReceiptItemRoute: Routes = [
  {
    path: '',
    component: CashierReceiptItemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincashierCashierReceiptItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CashierReceiptItemDetailComponent,
    resolve: {
      cashierReceiptItem: CashierReceiptItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierReceiptItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CashierReceiptItemUpdateComponent,
    resolve: {
      cashierReceiptItem: CashierReceiptItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierReceiptItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CashierReceiptItemUpdateComponent,
    resolve: {
      cashierReceiptItem: CashierReceiptItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierReceiptItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
