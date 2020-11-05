import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICashierProduct, CashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';
import { CashierProductService } from './cashier-product.service';
import { CashierProductComponent } from './cashier-product.component';
import { CashierProductDetailComponent } from './cashier-product-detail.component';
import { CashierProductUpdateComponent } from './cashier-product-update.component';

@Injectable({ providedIn: 'root' })
export class CashierProductResolve implements Resolve<ICashierProduct> {
  constructor(private service: CashierProductService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICashierProduct> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cashierProduct: HttpResponse<CashierProduct>) => {
          if (cashierProduct.body) {
            return of(cashierProduct.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CashierProduct());
  }
}

export const cashierProductRoute: Routes = [
  {
    path: '',
    component: CashierProductComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincashierCashierProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CashierProductDetailComponent,
    resolve: {
      cashierProduct: CashierProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CashierProductUpdateComponent,
    resolve: {
      cashierProduct: CashierProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CashierProductUpdateComponent,
    resolve: {
      cashierProduct: CashierProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincashierCashierProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
