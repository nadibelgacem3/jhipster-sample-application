import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITVAItem } from 'app/shared/model/mooinbase/tva-item.model';

@Component({
  selector: 'jhi-tva-item-detail',
  templateUrl: './tva-item-detail.component.html',
})
export class TVAItemDetailComponent implements OnInit {
  tVAItem: ITVAItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tVAItem }) => (this.tVAItem = tVAItem));
  }

  previousState(): void {
    window.history.back();
  }
}
