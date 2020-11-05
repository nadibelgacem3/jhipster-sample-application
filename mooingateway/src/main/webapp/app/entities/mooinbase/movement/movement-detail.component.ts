import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMovement } from 'app/shared/model/mooinbase/movement.model';

@Component({
  selector: 'jhi-movement-detail',
  templateUrl: './movement-detail.component.html',
})
export class MovementDetailComponent implements OnInit {
  movement: IMovement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ movement }) => (this.movement = movement));
  }

  previousState(): void {
    window.history.back();
  }
}
