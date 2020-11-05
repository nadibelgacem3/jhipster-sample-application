import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICashierCostumer, CashierCostumer } from 'app/shared/model/mooincashier/cashier-costumer.model';
import { CashierCostumerService } from './cashier-costumer.service';
import { CashierCostumerComponent } from './cashier-costumer.component';
import { CashierCostumerDetailComponent } from './cashier-costumer-detail.component';
import { CashierCostumerUpdateComponent } from './cashier-costumer-update.component';

@Injectable({ providedIn: 'root' })
export class CashierCostumerResolve implements Resolve<ICashierCostumer> {
  constructor(private service: CashierCostumerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICashierCostumer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cashierCostumer: HttpResponse<CashierCostumer>) => {
          if (cashierCostumer.body) {
            return of(cashierCostumer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CashierCostumer());
  }
}

export const cashierCostumerRoute: Routes = [
  {
    path: '',
    component: CashierCostumerComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincashierCashierCostumer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CashierCostumerDetailComponent,
    resolve: {
      cashierCostumer: CashierCostumerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierCostumer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CashierCostumerUpdateComponent,
    resolve: {
      cashierCostumer: CashierCostumerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierCostumer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CashierCostumerUpdateComponent,
    resolve: {
      cashierCostumer: CashierCostumerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierCostumer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
