import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductMark, ProductMark } from 'app/shared/model/mooinbase/product-mark.model';
import { ProductMarkService } from './product-mark.service';
import { ProductMarkComponent } from './product-mark.component';
import { ProductMarkDetailComponent } from './product-mark-detail.component';
import { ProductMarkUpdateComponent } from './product-mark-update.component';

@Injectable({ providedIn: 'root' })
export class ProductMarkResolve implements Resolve<IProductMark> {
  constructor(private service: ProductMarkService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductMark> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productMark: HttpResponse<ProductMark>) => {
          if (productMark.body) {
            return of(productMark.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductMark());
  }
}

export const productMarkRoute: Routes = [
  {
    path: '',
    component: ProductMarkComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinbaseProductMark.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductMarkDetailComponent,
    resolve: {
      productMark: ProductMarkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseProductMark.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductMarkUpdateComponent,
    resolve: {
      productMark: ProductMarkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseProductMark.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductMarkUpdateComponent,
    resolve: {
      productMark: ProductMarkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseProductMark.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
