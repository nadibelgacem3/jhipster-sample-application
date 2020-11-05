import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvoirItem } from 'app/shared/model/mooinbase/avoir-item.model';

@Component({
  selector: 'jhi-avoir-item-detail',
  templateUrl: './avoir-item-detail.component.html',
})
export class AvoirItemDetailComponent implements OnInit {
  avoirItem: IAvoirItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avoirItem }) => (this.avoirItem = avoirItem));
  }

  previousState(): void {
    window.history.back();
  }
}
