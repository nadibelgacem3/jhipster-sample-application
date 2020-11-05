import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITiers, Tiers } from 'app/shared/model/mooinbase/tiers.model';
import { TiersService } from './tiers.service';
import { TiersComponent } from './tiers.component';
import { TiersDetailComponent } from './tiers-detail.component';
import { TiersUpdateComponent } from './tiers-update.component';

@Injectable({ providedIn: 'root' })
export class TiersResolve implements Resolve<ITiers> {
  constructor(private service: TiersService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITiers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tiers: HttpResponse<Tiers>) => {
          if (tiers.body) {
            return of(tiers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tiers());
  }
}

export const tiersRoute: Routes = [
  {
    path: '',
    component: TiersComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinbaseTiers.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TiersDetailComponent,
    resolve: {
      tiers: TiersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTiers.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TiersUpdateComponent,
    resolve: {
      tiers: TiersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTiers.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TiersUpdateComponent,
    resolve: {
      tiers: TiersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTiers.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
