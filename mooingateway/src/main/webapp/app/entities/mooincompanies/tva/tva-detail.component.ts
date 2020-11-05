import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITVA } from 'app/shared/model/mooincompanies/tva.model';

@Component({
  selector: 'jhi-tva-detail',
  templateUrl: './tva-detail.component.html',
})
export class TVADetailComponent implements OnInit {
  tVA: ITVA | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tVA }) => (this.tVA = tVA));
  }

  previousState(): void {
    window.history.back();
  }
}
