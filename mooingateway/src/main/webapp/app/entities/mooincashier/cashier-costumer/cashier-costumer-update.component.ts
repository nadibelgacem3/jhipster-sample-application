import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICashierCostumer, CashierCostumer } from 'app/shared/model/mooincashier/cashier-costumer.model';
import { CashierCostumerService } from './cashier-costumer.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICashierLocation } from 'app/shared/model/mooincashier/cashier-location.model';
import { CashierLocationService } from 'app/entities/mooincashier/cashier-location/cashier-location.service';
import { ICashier } from 'app/shared/model/mooincashier/cashier.model';
import { CashierService } from 'app/entities/mooincashier/cashier/cashier.service';

type SelectableEntity = ICashierLocation | ICashier;

@Component({
  selector: 'jhi-cashier-costumer-update',
  templateUrl: './cashier-costumer-update.component.html',
})
export class CashierCostumerUpdateComponent implements OnInit {
  isSaving = false;
  cashierlocations: ICashierLocation[] = [];
  cashiers: ICashier[] = [];

  editForm = this.fb.group({
    id: [],
    reference: [null, [Validators.pattern('^SC[0-9]{3}COM[0-9]{2}$')]],
    firstName: [null, [Validators.required, Validators.minLength(4)]],
    lastName: [null, [Validators.required, Validators.minLength(3)]],
    phone1: [null, [Validators.minLength(8)]],
    phone2: [null, [Validators.minLength(8)]],
    image: [],
    imageContentType: [],
    email: [null, [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    cashierLocation: [],
    cashier: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected cashierCostumerService: CashierCostumerService,
    protected cashierLocationService: CashierLocationService,
    protected cashierService: CashierService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierCostumer }) => {
      this.updateForm(cashierCostumer);

      this.cashierLocationService
        .query({ filter: 'cashiercostumer-is-null' })
        .pipe(
          map((res: HttpResponse<ICashierLocation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICashierLocation[]) => {
          if (!cashierCostumer.cashierLocation || !cashierCostumer.cashierLocation.id) {
            this.cashierlocations = resBody;
          } else {
            this.cashierLocationService
              .find(cashierCostumer.cashierLocation.id)
              .pipe(
                map((subRes: HttpResponse<ICashierLocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICashierLocation[]) => (this.cashierlocations = concatRes));
          }
        });

      this.cashierService.query().subscribe((res: HttpResponse<ICashier[]>) => (this.cashiers = res.body || []));
    });
  }

  updateForm(cashierCostumer: ICashierCostumer): void {
    this.editForm.patchValue({
      id: cashierCostumer.id,
      reference: cashierCostumer.reference,
      firstName: cashierCostumer.firstName,
      lastName: cashierCostumer.lastName,
      phone1: cashierCostumer.phone1,
      phone2: cashierCostumer.phone2,
      image: cashierCostumer.image,
      imageContentType: cashierCostumer.imageContentType,
      email: cashierCostumer.email,
      cashierLocation: cashierCostumer.cashierLocation,
      cashier: cashierCostumer.cashier,
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
    const cashierCostumer = this.createFromForm();
    if (cashierCostumer.id !== undefined) {
      this.subscribeToSaveResponse(this.cashierCostumerService.update(cashierCostumer));
    } else {
      this.subscribeToSaveResponse(this.cashierCostumerService.create(cashierCostumer));
    }
  }

  private createFromForm(): ICashierCostumer {
    return {
      ...new CashierCostumer(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      phone1: this.editForm.get(['phone1'])!.value,
      phone2: this.editForm.get(['phone2'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      email: this.editForm.get(['email'])!.value,
      cashierLocation: this.editForm.get(['cashierLocation'])!.value,
      cashier: this.editForm.get(['cashier'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICashierCostumer>>): void {
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
