import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaxItem } from 'app/shared/model/mooinbase/tax-item.model';

@Component({
  selector: 'jhi-tax-item-detail',
  templateUrl: './tax-item-detail.component.html',
})
export class TaxItemDetailComponent implements OnInit {
  taxItem: ITaxItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxItem }) => (this.taxItem = taxItem));
  }

  previousState(): void {
    window.history.back();
  }
}
