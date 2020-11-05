import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBLItem } from 'app/shared/model/mooinbase/bl-item.model';

@Component({
  selector: 'jhi-bl-item-detail',
  templateUrl: './bl-item-detail.component.html',
})
export class BLItemDetailComponent implements OnInit {
  bLItem: IBLItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bLItem }) => (this.bLItem = bLItem));
  }

  previousState(): void {
    window.history.back();
  }
}
