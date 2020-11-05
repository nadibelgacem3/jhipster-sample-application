import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvoir } from 'app/shared/model/mooinbase/avoir.model';

@Component({
  selector: 'jhi-avoir-detail',
  templateUrl: './avoir-detail.component.html',
})
export class AvoirDetailComponent implements OnInit {
  avoir: IAvoir | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avoir }) => (this.avoir = avoir));
  }

  previousState(): void {
    window.history.back();
  }
}
