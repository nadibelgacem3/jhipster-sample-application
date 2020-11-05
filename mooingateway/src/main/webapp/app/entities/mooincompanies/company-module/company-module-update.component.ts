import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompanyModule, CompanyModule } from 'app/shared/model/mooincompanies/company-module.model';
import { CompanyModuleService } from './company-module.service';
import { ICompany } from 'app/shared/model/mooincompanies/company.model';
import { CompanyService } from 'app/entities/mooincompanies/company/company.service';

@Component({
  selector: 'jhi-company-module-update',
  templateUrl: './company-module-update.component.html',
})
export class CompanyModuleUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];
  activatedAtDp: any;
  activatedUntilDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    activatedAt: [null, [Validators.required]],
    activatedUntil: [null, [Validators.required]],
    isActivated: [null, [Validators.required]],
    price: [null, [Validators.required, Validators.min(0)]],
    company: [],
  });

  constructor(
    protected companyModuleService: CompanyModuleService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyModule }) => {
      this.updateForm(companyModule);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(companyModule: ICompanyModule): void {
    this.editForm.patchValue({
      id: companyModule.id,
      name: companyModule.name,
      activatedAt: companyModule.activatedAt,
      activatedUntil: companyModule.activatedUntil,
      isActivated: companyModule.isActivated,
      price: companyModule.price,
      company: companyModule.company,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyModule = this.createFromForm();
    if (companyModule.id !== undefined) {
      this.subscribeToSaveResponse(this.companyModuleService.update(companyModule));
    } else {
      this.subscribeToSaveResponse(this.companyModuleService.create(companyModule));
    }
  }

  private createFromForm(): ICompanyModule {
    return {
      ...new CompanyModule(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      activatedAt: this.editForm.get(['activatedAt'])!.value,
      activatedUntil: this.editForm.get(['activatedUntil'])!.value,
      isActivated: this.editForm.get(['isActivated'])!.value,
      price: this.editForm.get(['price'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyModule>>): void {
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
