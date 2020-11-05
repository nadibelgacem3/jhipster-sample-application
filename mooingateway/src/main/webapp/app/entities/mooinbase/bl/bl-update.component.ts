import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBL, BL } from 'app/shared/model/mooinbase/bl.model';
import { BLService } from './bl.service';
import { ITiers } from 'app/shared/model/mooinbase/tiers.model';
import { TiersService } from 'app/entities/mooinbase/tiers/tiers.service';

@Component({
  selector: 'jhi-bl-update',
  templateUrl: './bl-update.component.html',
})
export class BLUpdateComponent implements OnInit {
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
    protected bLService: BLService,
    protected tiersService: TiersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bL }) => {
      this.updateForm(bL);

      this.tiersService.query().subscribe((res: HttpResponse<ITiers[]>) => (this.tiers = res.body || []));
    });
  }

  updateForm(bL: IBL): void {
    this.editForm.patchValue({
      id: bL.id,
      number: bL.number,
      reference: bL.reference,
      status: bL.status,
      type: bL.type,
      totalHT: bL.totalHT,
      totalTVA: bL.totalTVA,
      totaTTC: bL.totaTTC,
      date: bL.date,
      dueDate: bL.dueDate,
      isConverted: bL.isConverted,
      companyID: bL.companyID,
      tiers: bL.tiers,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bL = this.createFromForm();
    if (bL.id !== undefined) {
      this.subscribeToSaveResponse(this.bLService.update(bL));
    } else {
      this.subscribeToSaveResponse(this.bLService.create(bL));
    }
  }

  private createFromForm(): IBL {
    return {
      ...new BL(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBL>>): void {
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
