import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICashierAppro, CashierAppro } from 'app/shared/model/mooincashier/cashier-appro.model';
import { CashierApproService } from './cashier-appro.service';
import { CashierApproComponent } from './cashier-appro.component';
import { CashierApproDetailComponent } from './cashier-appro-detail.component';
import { CashierApproUpdateComponent } from './cashier-appro-update.component';

@Injectable({ providedIn: 'root' })
export class CashierApproResolve implements Resolve<ICashierAppro> {
  constructor(private service: CashierApproService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICashierAppro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cashierAppro: HttpResponse<CashierAppro>) => {
          if (cashierAppro.body) {
            return of(cashierAppro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CashierAppro());
  }
}

export const cashierApproRoute: Routes = [
  {
    path: '',
    component: CashierApproComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincashierCashierAppro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CashierApproDetailComponent,
    resolve: {
      cashierAppro: CashierApproResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierAppro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CashierApproUpdateComponent,
    resolve: {
      cashierAppro: CashierApproResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierAppro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CashierApproUpdateComponent,
    resolve: {
      cashierAppro: CashierApproResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierAppro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
