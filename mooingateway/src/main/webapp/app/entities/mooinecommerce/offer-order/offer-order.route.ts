import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOfferOrder, OfferOrder } from 'app/shared/model/mooinecommerce/offer-order.model';
import { OfferOrderService } from './offer-order.service';
import { OfferOrderComponent } from './offer-order.component';
import { OfferOrderDetailComponent } from './offer-order-detail.component';
import { OfferOrderUpdateComponent } from './offer-order-update.component';

@Injectable({ providedIn: 'root' })
export class OfferOrderResolve implements Resolve<IOfferOrder> {
  constructor(private service: OfferOrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOfferOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((offerOrder: HttpResponse<OfferOrder>) => {
          if (offerOrder.body) {
            return of(offerOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OfferOrder());
  }
}

export const offerOrderRoute: Routes = [
  {
    path: '',
    component: OfferOrderComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinecommerceOfferOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OfferOrderDetailComponent,
    resolve: {
      offerOrder: OfferOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinecommerceOfferOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OfferOrderUpdateComponent,
    resolve: {
      offerOrder: OfferOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinecommerceOfferOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OfferOrderUpdateComponent,
    resolve: {
      offerOrder: OfferOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinecommerceOfferOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
