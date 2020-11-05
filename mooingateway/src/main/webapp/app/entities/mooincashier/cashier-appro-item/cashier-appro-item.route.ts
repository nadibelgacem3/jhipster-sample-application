import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICashierApproItem, CashierApproItem } from 'app/shared/model/mooincashier/cashier-appro-item.model';
import { CashierApproItemService } from './cashier-appro-item.service';
import { CashierApproItemComponent } from './cashier-appro-item.component';
import { CashierApproItemDetailComponent } from './cashier-appro-item-detail.component';
import { CashierApproItemUpdateComponent } from './cashier-appro-item-update.component';

@Injectable({ providedIn: 'root' })
export class CashierApproItemResolve implements Resolve<ICashierApproItem> {
  constructor(private service: CashierApproItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICashierApproItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cashierApproItem: HttpResponse<CashierApproItem>) => {
          if (cashierApproItem.body) {
            return of(cashierApproItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CashierApproItem());
  }
}

export const cashierApproItemRoute: Routes = [
  {
    path: '',
    component: CashierApproItemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincashierCashierApproItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CashierApproItemDetailComponent,
    resolve: {
      cashierApproItem: CashierApproItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierApproItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CashierApproItemUpdateComponent,
    resolve: {
      cashierApproItem: CashierApproItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierApproItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CashierApproItemUpdateComponent,
    resolve: {
      cashierApproItem: CashierApproItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierApproItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
