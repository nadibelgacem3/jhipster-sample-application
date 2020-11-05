import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuoteItem, QuoteItem } from 'app/shared/model/mooinbase/quote-item.model';
import { QuoteItemService } from './quote-item.service';
import { IProduct } from 'app/shared/model/mooinbase/product.model';
import { ProductService } from 'app/entities/mooinbase/product/product.service';
import { IQuote } from 'app/shared/model/mooinbase/quote.model';
import { QuoteService } from 'app/entities/mooinbase/quote/quote.service';

type SelectableEntity = IProduct | IQuote;

@Component({
  selector: 'jhi-quote-item-update',
  templateUrl: './quote-item-update.component.html',
})
export class QuoteItemUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  quotes: IQuote[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.min(0)]],
    discountRate: [null, [Validators.min(0)]],
    totalHT: [null, [Validators.min(0)]],
    totalTVA: [null, [Validators.min(0)]],
    totaTTC: [null, [Validators.min(0)]],
    product: [],
    quote: [],
  });

  constructor(
    protected quoteItemService: QuoteItemService,
    protected productService: ProductService,
    protected quoteService: QuoteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quoteItem }) => {
      this.updateForm(quoteItem);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.quoteService.query().subscribe((res: HttpResponse<IQuote[]>) => (this.quotes = res.body || []));
    });
  }

  updateForm(quoteItem: IQuoteItem): void {
    this.editForm.patchValue({
      id: quoteItem.id,
      quantity: quoteItem.quantity,
      discountRate: quoteItem.discountRate,
      totalHT: quoteItem.totalHT,
      totalTVA: quoteItem.totalTVA,
      totaTTC: quoteItem.totaTTC,
      product: quoteItem.product,
      quote: quoteItem.quote,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const quoteItem = this.createFromForm();
    if (quoteItem.id !== undefined) {
      this.subscribeToSaveResponse(this.quoteItemService.update(quoteItem));
    } else {
      this.subscribeToSaveResponse(this.quoteItemService.create(quoteItem));
    }
  }

  private createFromForm(): IQuoteItem {
    return {
      ...new QuoteItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      discountRate: this.editForm.get(['discountRate'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      product: this.editForm.get(['product'])!.value,
      quote: this.editForm.get(['quote'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuoteItem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
