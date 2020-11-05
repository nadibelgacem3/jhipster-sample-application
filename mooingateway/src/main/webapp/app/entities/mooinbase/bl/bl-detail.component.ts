import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBL } from 'app/shared/model/mooinbase/bl.model';

@Component({
  selector: 'jhi-bl-detail',
  templateUrl: './bl-detail.component.html',
})
export class BLDetailComponent implements OnInit {
  bL: IBL | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bL }) => (this.bL = bL));
  }

  previousState(): void {
    window.history.back();
  }
}
