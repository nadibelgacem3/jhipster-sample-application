import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuote, Quote } from 'app/shared/model/mooinbase/quote.model';
import { QuoteService } from './quote.service';
import { ITiers } from 'app/shared/model/mooinbase/tiers.model';
import { TiersService } from 'app/entities/mooinbase/tiers/tiers.service';

@Component({
  selector: 'jhi-quote-update',
  templateUrl: './quote-update.component.html',
})
export class QuoteUpdateComponent implements OnInit {
  isSaving = false;
  tiers: ITiers[] = [];
  dateDp: any;
  dueDateDp: any;

  editForm = this.fb.group({
    id: [],
    number: [],
    reference: [],
    status: [],
    type: [],
    totalHT: [null, [Validators.required, Validators.min(0)]],
    totalTVA: [null, [Validators.required, Validators.min(0)]],
    totaTTC: [null, [Validators.required, Validators.min(0)]],
    date: [null, [Validators.required]],
    dueDate: [],
    isConverted: [],
    companyID: [],
    tiers: [],
  });

  constructor(
    protected quoteService: QuoteService,
    protected tiersService: TiersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quote }) => {
      this.updateForm(quote);

      this.tiersService.query().subscribe((res: HttpResponse<ITiers[]>) => (this.tiers = res.body || []));
    });
  }

  updateForm(quote: IQuote): void {
    this.editForm.patchValue({
      id: quote.id,
      number: quote.number,
      reference: quote.reference,
      status: quote.status,
      type: quote.type,
      totalHT: quote.totalHT,
      totalTVA: quote.totalTVA,
      totaTTC: quote.totaTTC,
      date: quote.date,
      dueDate: quote.dueDate,
      isConverted: quote.isConverted,
      companyID: quote.companyID,
      tiers: quote.tiers,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const quote = this.createFromForm();
    if (quote.id !== undefined) {
      this.subscribeToSaveResponse(this.quoteService.update(quote));
    } else {
      this.subscribeToSaveResponse(this.quoteService.create(quote));
    }
  }

  private createFromForm(): IQuote {
    return {
      ...new Quote(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      status: this.editForm.get(['status'])!.value,
      type: this.editForm.get(['type'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      date: this.editForm.get(['date'])!.value,
      dueDate: this.editForm.get(['dueDate'])!.value,
      isConverted: this.editForm.get(['isConverted'])!.value,
      companyID: this.editForm.get(['companyID'])!.value,
      tiers: this.editForm.get(['tiers'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuote>>): void {
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

  trackById(index: number, item: ITiers): any {
    return item.id;
  }
}
