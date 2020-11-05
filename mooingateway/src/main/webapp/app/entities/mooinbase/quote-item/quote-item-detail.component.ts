import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuoteItem } from 'app/shared/model/mooinbase/quote-item.model';

@Component({
  selector: 'jhi-quote-item-detail',
  templateUrl: './quote-item-detail.component.html',
})
export class QuoteItemDetailComponent implements OnInit {
  quoteItem: IQuoteItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quoteItem }) => (this.quoteItem = quoteItem));
  }

  previousState(): void {
    window.history.back();
  }
}
