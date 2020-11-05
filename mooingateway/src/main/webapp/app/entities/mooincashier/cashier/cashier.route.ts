import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICashier, Cashier } from 'app/shared/model/mooincashier/cashier.model';
import { CashierService } from './cashier.service';
import { CashierComponent } from './cashier.component';
import { CashierDetailComponent } from './cashier-detail.component';
import { CashierUpdateComponent } from './cashier-update.component';

@Injectable({ providedIn: 'root' })
export class CashierResolve implements Resolve<ICashier> {
  constructor(private service: CashierService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICashier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cashier: HttpResponse<Cashier>) => {
          if (cashier.body) {
            return of(cashier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cashier());
  }
}

export const cashierRoute: Routes = [
  {
    path: '',
    component: CashierComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincashierCashier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CashierDetailComponent,
    resolve: {
      cashier: CashierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CashierUpdateComponent,
    resolve: {
      cashier: CashierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CashierUpdateComponent,
    resolve: {
      cashier: CashierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
