import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITiersLocation, TiersLocation } from 'app/shared/model/mooinbase/tiers-location.model';
import { TiersLocationService } from './tiers-location.service';
import { TiersLocationComponent } from './tiers-location.component';
import { TiersLocationDetailComponent } from './tiers-location-detail.component';
import { TiersLocationUpdateComponent } from './tiers-location-update.component';

@Injectable({ providedIn: 'root' })
export class TiersLocationResolve implements Resolve<ITiersLocation> {
  constructor(private service: TiersLocationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITiersLocation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tiersLocation: HttpResponse<TiersLocation>) => {
          if (tiersLocation.body) {
            return of(tiersLocation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TiersLocation());
  }
}

export const tiersLocationRoute: Routes = [
  {
    path: '',
    component: TiersLocationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinbaseTiersLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TiersLocationDetailComponent,
    resolve: {
      tiersLocation: TiersLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTiersLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TiersLocationUpdateComponent,
    resolve: {
      tiersLocation: TiersLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTiersLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TiersLocationUpdateComponent,
    resolve: {
      tiersLocation: TiersLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTiersLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
