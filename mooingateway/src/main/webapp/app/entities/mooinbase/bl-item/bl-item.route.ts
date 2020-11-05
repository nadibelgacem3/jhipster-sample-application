import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBLItem, BLItem } from 'app/shared/model/mooinbase/bl-item.model';
import { BLItemService } from './bl-item.service';
import { BLItemComponent } from './bl-item.component';
import { BLItemDetailComponent } from './bl-item-detail.component';
import { BLItemUpdateComponent } from './bl-item-update.component';

@Injectable({ providedIn: 'root' })
export class BLItemResolve implements Resolve<IBLItem> {
  constructor(private service: BLItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBLItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bLItem: HttpResponse<BLItem>) => {
          if (bLItem.body) {
            return of(bLItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BLItem());
  }
}

export const bLItemRoute: Routes = [
  {
    path: '',
    component: BLItemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinbaseBLItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BLItemDetailComponent,
    resolve: {
      bLItem: BLItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseBLItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BLItemUpdateComponent,
    resolve: {
      bLItem: BLItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseBLItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BLItemUpdateComponent,
    resolve: {
      bLItem: BLItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseBLItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
