import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAvoir, Avoir } from 'app/shared/model/mooinbase/avoir.model';
import { AvoirService } from './avoir.service';
import { ITiers } from 'app/shared/model/mooinbase/tiers.model';
import { TiersService } from 'app/entities/mooinbase/tiers/tiers.service';

@Component({
  selector: 'jhi-avoir-update',
  templateUrl: './avoir-update.component.html',
})
export class AvoirUpdateComponent implements OnInit {
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
    protected avoirService: AvoirService,
    protected tiersService: TiersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avoir }) => {
      this.updateForm(avoir);

      this.tiersService.query().subscribe((res: HttpResponse<ITiers[]>) => (this.tiers = res.body || []));
    });
  }

  updateForm(avoir: IAvoir): void {
    this.editForm.patchValue({
      id: avoir.id,
      number: avoir.number,
      reference: avoir.reference,
      status: avoir.status,
      type: avoir.type,
      totalHT: avoir.totalHT,
      totalTVA: avoir.totalTVA,
      totaTTC: avoir.totaTTC,
      date: avoir.date,
      dueDate: avoir.dueDate,
      isConverted: avoir.isConverted,
      companyID: avoir.companyID,
      tiers: avoir.tiers,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avoir = this.createFromForm();
    if (avoir.id !== undefined) {
      this.subscribeToSaveResponse(this.avoirService.update(avoir));
    } else {
      this.subscribeToSaveResponse(this.avoirService.create(avoir));
    }
  }

  private createFromForm(): IAvoir {
    return {
      ...new Avoir(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvoir>>): void {
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
