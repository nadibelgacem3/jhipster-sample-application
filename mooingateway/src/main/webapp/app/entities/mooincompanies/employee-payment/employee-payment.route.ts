import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployeePayment, EmployeePayment } from 'app/shared/model/mooincompanies/employee-payment.model';
import { EmployeePaymentService } from './employee-payment.service';
import { EmployeePaymentComponent } from './employee-payment.component';
import { EmployeePaymentDetailComponent } from './employee-payment-detail.component';
import { EmployeePaymentUpdateComponent } from './employee-payment-update.component';

@Injectable({ providedIn: 'root' })
export class EmployeePaymentResolve implements Resolve<IEmployeePayment> {
  constructor(private service: EmployeePaymentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeePayment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employeePayment: HttpResponse<EmployeePayment>) => {
          if (employeePayment.body) {
            return of(employeePayment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmployeePayment());
  }
}

export const employeePaymentRoute: Routes = [
  {
    path: '',
    component: EmployeePaymentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mooingatewayApp.mooincompaniesEmployeePayment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeePaymentDetailComponent,
    resolve: {
      employeePayment: EmployeePaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesEmployeePayment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeePaymentUpdateComponent,
    resolve: {
      employeePayment: EmployeePaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesEmployeePayment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeePaymentUpdateComponent,
    resolve: {
      employeePayment: EmployeePaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooincompaniesEmployeePayment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
