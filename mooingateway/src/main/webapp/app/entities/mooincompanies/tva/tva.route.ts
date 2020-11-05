import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITVA, TVA } from 'app/shared/model/mooincompanies/tva.model';
import { TVAService } from './tva.service';
import { TVAComponent } from './tva.component';
import { TVADetailComponent } from './tva-detail.component';
import { TVAUpdateComponent } from './tva-update.component';

@Injectable({ providedIn: 'root' })
export class TVAResolve implements Resolve<ITVA> {
  constructor(private service: TVAService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITVA> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tVA: HttpResponse<TVA>) => {
          if (tVA.body) {
            return of(tVA.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TVA());
  }
}

export const tVARoute: Routes = [
  {
    path: '',
    component: TVAComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincompaniesTVa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TVADetailComponent,
    resolve: {
      tVA: TVAResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesTVa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TVAUpdateComponent,
    resolve: {
      tVA: TVAResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesTVa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TVAUpdateComponent,
    resolve: {
      tVA: TVAResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesTVa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
