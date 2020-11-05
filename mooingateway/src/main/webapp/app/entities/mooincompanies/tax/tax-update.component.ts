import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITax, Tax } from 'app/shared/model/mooincompanies/tax.model';
import { TaxService } from './tax.service';
import { ICompany } from 'app/shared/model/mooincompanies/company.model';
import { CompanyService } from 'app/entities/mooincompanies/company/company.service';

@Component({
  selector: 'jhi-tax-update',
  templateUrl: './tax-update.component.html',
})
export class TaxUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    isValued: [null, [Validators.required]],
    isPercentage: [null, [Validators.required]],
    value: [null, [Validators.required]],
    company: [],
  });

  constructor(
    protected taxService: TaxService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tax }) => {
      this.updateForm(tax);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(tax: ITax): void {
    this.editForm.patchValue({
      id: tax.id,
      name: tax.name,
      isValued: tax.isValued,
      isPercentage: tax.isPercentage,
      value: tax.value,
      company: tax.company,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tax = this.createFromForm();
    if (tax.id !== undefined) {
      this.subscribeToSaveResponse(this.taxService.update(tax));
    } else {
      this.subscribeToSaveResponse(this.taxService.create(tax));
    }
  }

  private createFromForm(): ITax {
    return {
      ...new Tax(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      isValued: this.editForm.get(['isValued'])!.value,
      isPercentage: this.editForm.get(['isPercentage'])!.value,
      value: this.editForm.get(['value'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITax>>): void {
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
