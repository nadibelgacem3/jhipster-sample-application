import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductMark } from 'app/shared/model/mooinbase/product-mark.model';

@Component({
  selector: 'jhi-product-mark-detail',
  templateUrl: './product-mark-detail.component.html',
})
export class ProductMarkDetailComponent implements OnInit {
  productMark: IProductMark | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productMark }) => (this.productMark = productMark));
  }

  previousState(): void {
    window.history.back();
  }
}
