import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBL, BL } from 'app/shared/model/mooinbase/bl.model';
import { BLService } from './bl.service';
import { BLComponent } from './bl.component';
import { BLDetailComponent } from './bl-detail.component';
import { BLUpdateComponent } from './bl-update.component';

@Injectable({ providedIn: 'root' })
export class BLResolve implements Resolve<IBL> {
  constructor(private service: BLService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBL> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bL: HttpResponse<BL>) => {
          if (bL.body) {
            return of(bL.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BL());
  }
}

export const bLRoute: Routes = [
  {
    path: '',
    component: BLComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinbaseBL.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BLDetailComponent,
    resolve: {
      bL: BLResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseBL.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BLUpdateComponent,
    resolve: {
      bL: BLResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseBL.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BLUpdateComponent,
    resolve: {
      bL: BLResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseBL.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
