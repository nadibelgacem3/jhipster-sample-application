import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITiers, Tiers } from 'app/shared/model/mooinbase/tiers.model';
import { TiersService } from './tiers.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITiersLocation } from 'app/shared/model/mooinbase/tiers-location.model';
import { TiersLocationService } from 'app/entities/mooinbase/tiers-location/tiers-location.service';

@Component({
  selector: 'jhi-tiers-update',
  templateUrl: './tiers-update.component.html',
})
export class TiersUpdateComponent implements OnInit {
  isSaving = false;
  tierslocations: ITiersLocation[] = [];

  editForm = this.fb.group({
    id: [],
    reference: [],
    firstName: [null, [Validators.required, Validators.minLength(4)]],
    lastName: [null, [Validators.required, Validators.minLength(3)]],
    phone1: [null, [Validators.minLength(8)]],
    phone2: [null, [Validators.minLength(8)]],
    image: [],
    imageContentType: [],
    email: [null, [Validators.required, Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    type: [],
    isCustomer: [null, [Validators.required]],
    isSupplier: [null, [Validators.required]],
    companyID: [],
    tiersLocation: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected tiersService: TiersService,
    protected tiersLocationService: TiersLocationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tiers }) => {
      this.updateForm(tiers);

      this.tiersLocationService
        .query({ filter: 'tiers-is-null' })
        .pipe(
          map((res: HttpResponse<ITiersLocation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITiersLocation[]) => {
          if (!tiers.tiersLocation || !tiers.tiersLocation.id) {
            this.tierslocations = resBody;
          } else {
            this.tiersLocationService
              .find(tiers.tiersLocation.id)
              .pipe(
                map((subRes: HttpResponse<ITiersLocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITiersLocation[]) => (this.tierslocations = concatRes));
          }
        });
    });
  }

  updateForm(tiers: ITiers): void {
    this.editForm.patchValue({
      id: tiers.id,
      reference: tiers.reference,
      firstName: tiers.firstName,
      lastName: tiers.lastName,
      phone1: tiers.phone1,
      phone2: tiers.phone2,
      image: tiers.image,
      imageContentType: tiers.imageContentType,
      email: tiers.email,
      type: tiers.type,
      isCustomer: tiers.isCustomer,
      isSupplier: tiers.isSupplier,
      companyID: tiers.companyID,
      tiersLocation: tiers.tiersLocation,
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
    const tiers = this.createFromForm();
    if (tiers.id !== undefined) {
      this.subscribeToSaveResponse(this.tiersService.update(tiers));
    } else {
      this.subscribeToSaveResponse(this.tiersService.create(tiers));
    }
  }

  private createFromForm(): ITiers {
    return {
      ...new Tiers(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      phone1: this.editForm.get(['phone1'])!.value,
      phone2: this.editForm.get(['phone2'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      email: this.editForm.get(['email'])!.value,
      type: this.editForm.get(['type'])!.value,
      isCustomer: this.editForm.get(['isCustomer'])!.value,
      isSupplier: this.editForm.get(['isSupplier'])!.value,
      companyID: this.editForm.get(['companyID'])!.value,
      tiersLocation: this.editForm.get(['tiersLocation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITiers>>): void {
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

  trackById(index: number, item: ITiersLocation): any {
    return item.id;
  }
}
