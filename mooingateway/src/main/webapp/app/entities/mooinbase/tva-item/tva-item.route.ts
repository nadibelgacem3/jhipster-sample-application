import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITVAItem, TVAItem } from 'app/shared/model/mooinbase/tva-item.model';
import { TVAItemService } from './tva-item.service';
import { TVAItemComponent } from './tva-item.component';
import { TVAItemDetailComponent } from './tva-item-detail.component';
import { TVAItemUpdateComponent } from './tva-item-update.component';

@Injectable({ providedIn: 'root' })
export class TVAItemResolve implements Resolve<ITVAItem> {
  constructor(private service: TVAItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITVAItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tVAItem: HttpResponse<TVAItem>) => {
          if (tVAItem.body) {
            return of(tVAItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TVAItem());
  }
}

export const tVAItemRoute: Routes = [
  {
    path: '',
    component: TVAItemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinbaseTVaItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TVAItemDetailComponent,
    resolve: {
      tVAItem: TVAItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTVaItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TVAItemUpdateComponent,
    resolve: {
      tVAItem: TVAItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTVaItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TVAItemUpdateComponent,
    resolve: {
      tVAItem: TVAItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTVaItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
