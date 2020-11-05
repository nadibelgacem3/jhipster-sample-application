import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaxItem, TaxItem } from 'app/shared/model/mooinbase/tax-item.model';
import { TaxItemService } from './tax-item.service';
import { TaxItemComponent } from './tax-item.component';
import { TaxItemDetailComponent } from './tax-item-detail.component';
import { TaxItemUpdateComponent } from './tax-item-update.component';

@Injectable({ providedIn: 'root' })
export class TaxItemResolve implements Resolve<ITaxItem> {
  constructor(private service: TaxItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaxItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taxItem: HttpResponse<TaxItem>) => {
          if (taxItem.body) {
            return of(taxItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaxItem());
  }
}

export const taxItemRoute: Routes = [
  {
    path: '',
    component: TaxItemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinbaseTaxItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaxItemDetailComponent,
    resolve: {
      taxItem: TaxItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTaxItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaxItemUpdateComponent,
    resolve: {
      taxItem: TaxItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTaxItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaxItemUpdateComponent,
    resolve: {
      taxItem: TaxItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseTaxItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
