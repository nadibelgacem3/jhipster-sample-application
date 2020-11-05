import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuoteItem, QuoteItem } from 'app/shared/model/mooinbase/quote-item.model';
import { QuoteItemService } from './quote-item.service';
import { QuoteItemComponent } from './quote-item.component';
import { QuoteItemDetailComponent } from './quote-item-detail.component';
import { QuoteItemUpdateComponent } from './quote-item-update.component';

@Injectable({ providedIn: 'root' })
export class QuoteItemResolve implements Resolve<IQuoteItem> {
  constructor(private service: QuoteItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuoteItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((quoteItem: HttpResponse<QuoteItem>) => {
          if (quoteItem.body) {
            return of(quoteItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuoteItem());
  }
}

export const quoteItemRoute: Routes = [
  {
    path: '',
    component: QuoteItemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooinbaseQuoteItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuoteItemDetailComponent,
    resolve: {
      quoteItem: QuoteItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseQuoteItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuoteItemUpdateComponent,
    resolve: {
      quoteItem: QuoteItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseQuoteItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuoteItemUpdateComponent,
    resolve: {
      quoteItem: QuoteItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseQuoteItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
