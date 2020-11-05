import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITiersBankCheck, TiersBankCheck } from 'app/shared/model/mooinbase/tiers-bank-check.model';
import { TiersBankCheckService } from './tiers-bank-check.service';
import { TiersBankCheckComponent } from './tiers-bank-check.component';
import { TiersBankCheckDetailComponent } from './tiers-bank-check-detail.component';
import { TiersBankCheckUpdateComponent } from './tiers-bank-check-update.component';

@Injectable({ providedIn: 'root' })
export class TiersBankCheckResolve implements Resolve<ITiersBankCheck> {
  constructor(private service: TiersBankCheckService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITiersBankCheck> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tiersBankCheck: HttpResponse<TiersBankCheck>) => {
          if (tiersBankCheck.body) {
            return of(tiersBankCheck.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TiersBankCheck());
  }
}

export const tiersBankCheckRoute: Routes = [
  {
    path: '',
    component: TiersBankCheckComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinbaseTiersBankCheck.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TiersBankCheckDetailComponent,
    resolve: {
      tiersBankCheck: TiersBankCheckResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTiersBankCheck.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TiersBankCheckUpdateComponent,
    resolve: {
      tiersBankCheck: TiersBankCheckResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTiersBankCheck.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TiersBankCheckUpdateComponent,
    resolve: {
      tiersBankCheck: TiersBankCheckResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTiersBankCheck.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
