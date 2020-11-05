import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITiersBankCheck, TiersBankCheck } from 'app/shared/model/mooinbase/tiers-bank-check.model';
import { TiersBankCheckService } from './tiers-bank-check.service';
import { ITiers } from 'app/shared/model/mooinbase/tiers.model';
import { TiersService } from 'app/entities/mooinbase/tiers/tiers.service';

@Component({
  selector: 'jhi-tiers-bank-check-update',
  templateUrl: './tiers-bank-check-update.component.html',
})
export class TiersBankCheckUpdateComponent implements OnInit {
  isSaving = false;
  tiers: ITiers[] = [];
  dueDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    bankName: [],
    number: [],
    amount: [null, [Validators.required]],
    dueDate: [null, [Validators.required]],
    bankCheckType: [null, [Validators.required]],
    bankCheckKind: [null, [Validators.required]],
    swift: [],
    type: [],
    tiers: [],
  });

  constructor(
    protected tiersBankCheckService: TiersBankCheckService,
    protected tiersService: TiersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tiersBankCheck }) => {
      this.updateForm(tiersBankCheck);

      this.tiersService.query().subscribe((res: HttpResponse<ITiers[]>) => (this.tiers = res.body || []));
    });
  }

  updateForm(tiersBankCheck: ITiersBankCheck): void {
    this.editForm.patchValue({
      id: tiersBankCheck.id,
      name: tiersBankCheck.name,
      bankName: tiersBankCheck.bankName,
      number: tiersBankCheck.number,
      amount: tiersBankCheck.amount,
      dueDate: tiersBankCheck.dueDate,
      bankCheckType: tiersBankCheck.bankCheckType,
      bankCheckKind: tiersBankCheck.bankCheckKind,
      swift: tiersBankCheck.swift,
      type: tiersBankCheck.type,
      tiers: tiersBankCheck.tiers,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tiersBankCheck = this.createFromForm();
    if (tiersBankCheck.id !== undefined) {
      this.subscribeToSaveResponse(this.tiersBankCheckService.update(tiersBankCheck));
    } else {
      this.subscribeToSaveResponse(this.tiersBankCheckService.create(tiersBankCheck));
    }
  }

  private createFromForm(): ITiersBankCheck {
    return {
      ...new TiersBankCheck(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      number: this.editForm.get(['number'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      dueDate: this.editForm.get(['dueDate'])!.value,
      bankCheckType: this.editForm.get(['bankCheckType'])!.value,
      bankCheckKind: this.editForm.get(['bankCheckKind'])!.value,
      swift: this.editForm.get(['swift'])!.value,
      type: this.editForm.get(['type'])!.value,
      tiers: this.editForm.get(['tiers'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITiersBankCheck>>): void {
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
