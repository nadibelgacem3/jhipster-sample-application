import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITVA, TVA } from 'app/shared/model/mooincompanies/tva.model';
import { TVAService } from './tva.service';
import { ICompany } from 'app/shared/model/mooincompanies/company.model';
import { CompanyService } from 'app/entities/mooincompanies/company/company.service';

@Component({
  selector: 'jhi-tva-update',
  templateUrl: './tva-update.component.html',
})
export class TVAUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    percentageValue: [null, [Validators.required]],
    company: [],
  });

  constructor(
    protected tVAService: TVAService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tVA }) => {
      this.updateForm(tVA);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(tVA: ITVA): void {
    this.editForm.patchValue({
      id: tVA.id,
      name: tVA.name,
      percentageValue: tVA.percentageValue,
      company: tVA.company,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tVA = this.createFromForm();
    if (tVA.id !== undefined) {
      this.subscribeToSaveResponse(this.tVAService.update(tVA));
    } else {
      this.subscribeToSaveResponse(this.tVAService.create(tVA));
    }
  }

  private createFromForm(): ITVA {
    return {
      ...new TVA(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      percentageValue: this.editForm.get(['percentageValue'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITVA>>): void {
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

  trackById(index: number, item: ICompany): any {
    return item.id;
  }
}
