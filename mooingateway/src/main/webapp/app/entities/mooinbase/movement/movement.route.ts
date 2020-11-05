import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMovement, Movement } from 'app/shared/model/mooinbase/movement.model';
import { MovementService } from './movement.service';
import { MovementComponent } from './movement.component';
import { MovementDetailComponent } from './movement-detail.component';
import { MovementUpdateComponent } from './movement-update.component';

@Injectable({ providedIn: 'root' })
export class MovementResolve implements Resolve<IMovement> {
  constructor(private service: MovementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMovement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((movement: HttpResponse<Movement>) => {
          if (movement.body) {
            return of(movement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Movement());
  }
}

export const movementRoute: Routes = [
  {
    path: '',
    component: MovementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseMovement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MovementDetailComponent,
    resolve: {
      movement: MovementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseMovement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MovementUpdateComponent,
    resolve: {
      movement: MovementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseMovement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MovementUpdateComponent,
    resolve: {
      movement: MovementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mooingatewayApp.mooinbaseMovement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
