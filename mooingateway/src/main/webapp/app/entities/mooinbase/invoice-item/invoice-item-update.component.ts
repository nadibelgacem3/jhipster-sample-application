import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInvoiceItem, InvoiceItem } from 'app/shared/model/mooinbase/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';
import { IProduct } from 'app/shared/model/mooinbase/product.model';
import { ProductService } from 'app/entities/mooinbase/product/product.service';
import { IInvoice } from 'app/shared/model/mooinbase/invoice.model';
import { InvoiceService } from 'app/entities/mooinbase/invoice/invoice.service';

type SelectableEntity = IProduct | IInvoice;

@Component({
  selector: 'jhi-invoice-item-update',
  templateUrl: './invoice-item-update.component.html',
})
export class InvoiceItemUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  invoices: IInvoice[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.min(0)]],
    discountRate: [null, [Validators.min(0)]],
    totalHT: [null, [Validators.min(0)]],
    totalTVA: [null, [Validators.min(0)]],
    totaTTC: [null, [Validators.min(0)]],
    product: [],
    invoice: [],
  });

  constructor(
    protected invoiceItemService: InvoiceItemService,
    protected productService: ProductService,
    protected invoiceService: InvoiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceItem }) => {
      this.updateForm(invoiceItem);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.invoiceService.query().subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body || []));
    });
  }

  updateForm(invoiceItem: IInvoiceItem): void {
    this.editForm.patchValue({
      id: invoiceItem.id,
      quantity: invoiceItem.quantity,
      discountRate: invoiceItem.discountRate,
      totalHT: invoiceItem.totalHT,
      totalTVA: invoiceItem.totalTVA,
      totaTTC: invoiceItem.totaTTC,
      product: invoiceItem.product,
      invoice: invoiceItem.invoice,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoiceItem = this.createFromForm();
    if (invoiceItem.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceItemService.update(invoiceItem));
    } else {
      this.subscribeToSaveResponse(this.invoiceItemService.create(invoiceItem));
    }
  }

  private createFromForm(): IInvoiceItem {
    return {
      ...new InvoiceItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      discountRate: this.editForm.get(['discountRate'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      product: this.editForm.get(['product'])!.value,
      invoice: this.editForm.get(['invoice'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceItem>>): void {
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
