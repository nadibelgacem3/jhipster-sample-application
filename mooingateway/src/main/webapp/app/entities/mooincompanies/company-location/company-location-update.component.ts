import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICompanyLocation, CompanyLocation } from 'app/shared/model/mooincompanies/company-location.model';
import { CompanyLocationService } from './company-location.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-company-location-update',
  templateUrl: './company-location-update.component.html',
})
export class CompanyLocationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    localNumber: [],
    streetAddress: [],
    postalCode: [],
    city: [],
    stateProvince: [],
    countryName: [],
    flag: [],
    flagContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected companyLocationService: CompanyLocationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyLocation }) => {
      this.updateForm(companyLocation);
    });
  }

  updateForm(companyLocation: ICompanyLocation): void {
    this.editForm.patchValue({
      id: companyLocation.id,
      localNumber: companyLocation.localNumber,
      streetAddress: companyLocation.streetAddress,
      postalCode: companyLocation.postalCode,
      city: companyLocation.city,
      stateProvince: companyLocation.stateProvince,
      countryName: companyLocation.countryName,
      flag: companyLocation.flag,
      flagContentType: companyLocation.flagContentType,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('mooingatewayApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyLocation = this.createFromForm();
    if (companyLocation.id !== undefined) {
      this.subscribeToSaveResponse(this.companyLocationService.update(companyLocation));
    } else {
      this.subscribeToSaveResponse(this.companyLocationService.create(companyLocation));
    }
  }

  private createFromForm(): ICompanyLocation {
    return {
      ...new CompanyLocation(),
      id: this.editForm.get(['id'])!.value,
      localNumber: this.editForm.get(['localNumber'])!.value,
      streetAddress: this.editForm.get(['streetAddress'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      city: this.editForm.get(['city'])!.value,
      stateProvince: this.editForm.get(['stateProvince'])!.value,
      countryName: this.editForm.get(['countryName'])!.value,
      flagContentType: this.editForm.get(['flagContentType'])!.value,
      flag: this.editForm.get(['flag'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyLocation>>): void {
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
}
