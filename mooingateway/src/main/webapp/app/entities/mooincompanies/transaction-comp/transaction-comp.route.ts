import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransactionComp, TransactionComp } from 'app/shared/model/mooincompanies/transaction-comp.model';
import { TransactionCompService } from './transaction-comp.service';
import { TransactionCompComponent } from './transaction-comp.component';
import { TransactionCompDetailComponent } from './transaction-comp-detail.component';
import { TransactionCompUpdateComponent } from './transaction-comp-update.component';

@Injectable({ providedIn: 'root' })
export class TransactionCompResolve implements Resolve<ITransactionComp> {
  constructor(private service: TransactionCompService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransactionComp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transactionComp: HttpResponse<TransactionComp>) => {
          if (transactionComp.body) {
            return of(transactionComp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransactionComp());
  }
}

export const transactionCompRoute: Routes = [
  {
    path: '',
    component: TransactionCompComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincompaniesTransactionComp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransactionCompDetailComponent,
    resolve: {
      transactionComp: TransactionCompResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesTransactionComp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransactionCompUpdateComponent,
    resolve: {
      transactionComp: TransactionCompResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesTransactionComp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransactionCompUpdateComponent,
    resolve: {
      transactionComp: TransactionCompResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesTransactionComp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
