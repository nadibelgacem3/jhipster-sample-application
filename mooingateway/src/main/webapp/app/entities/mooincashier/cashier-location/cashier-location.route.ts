import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICashierLocation, CashierLocation } from 'app/shared/model/mooincashier/cashier-location.model';
import { CashierLocationService } from './cashier-location.service';
import { CashierLocationComponent } from './cashier-location.component';
import { CashierLocationDetailComponent } from './cashier-location-detail.component';
import { CashierLocationUpdateComponent } from './cashier-location-update.component';

@Injectable({ providedIn: 'root' })
export class CashierLocationResolve implements Resolve<ICashierLocation> {
  constructor(private service: CashierLocationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICashierLocation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cashierLocation: HttpResponse<CashierLocation>) => {
          if (cashierLocation.body) {
            return of(cashierLocation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CashierLocation());
  }
}

export const cashierLocationRoute: Routes = [
  {
    path: '',
    component: CashierLocationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincashierCashierLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CashierLocationDetailComponent,
    resolve: {
      cashierLocation: CashierLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CashierLocationUpdateComponent,
    resolve: {
      cashierLocation: CashierLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CashierLocationUpdateComponent,
    resolve: {
      cashierLocation: CashierLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
