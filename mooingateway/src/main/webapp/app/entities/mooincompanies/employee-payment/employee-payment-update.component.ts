import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEmployeePayment, EmployeePayment } from 'app/shared/model/mooincompanies/employee-payment.model';
import { EmployeePaymentService } from './employee-payment.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/mooincompanies/employee.model';
import { EmployeeService } from 'app/entities/mooincompanies/employee/employee.service';

@Component({
  selector: 'jhi-employee-payment-update',
  templateUrl: './employee-payment-update.component.html',
})
export class EmployeePaymentUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  fromDateDp: any;
  toDateDp: any;

  editForm = this.fb.group({
    id: [],
    details: [],
    amount: [null, [Validators.required, Validators.min(0)]],
    paymentDate: [],
    fromDate: [],
    toDate: [],
    imageJustif: [],
    imageJustifContentType: [],
    employee: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected employeePaymentService: EmployeePaymentService,
    protected employeeService: EmployeeService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeePayment }) => {
      if (!employeePayment.id) {
        const today = moment().startOf('day');
        employeePayment.paymentDate = today;
      }

      this.updateForm(employeePayment);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(employeePayment: IEmployeePayment): void {
    this.editForm.patchValue({
      id: employeePayment.id,
      details: employeePayment.details,
      amount: employeePayment.amount,
      paymentDate: employeePayment.paymentDate ? employeePayment.paymentDate.format(DATE_TIME_FORMAT) : null,
      fromDate: employeePayment.fromDate,
      toDate: employeePayment.toDate,
      imageJustif: employeePayment.imageJustif,
      imageJustifContentType: employeePayment.imageJustifContentType,
      employee: employeePayment.employee,
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
    const employeePayment = this.createFromForm();
    if (employeePayment.id !== undefined) {
      this.subscribeToSaveResponse(this.employeePaymentService.update(employeePayment));
    } else {
      this.subscribeToSaveResponse(this.employeePaymentService.create(employeePayment));
    }
  }

  private createFromForm(): IEmployeePayment {
    return {
      ...new EmployeePayment(),
      id: this.editForm.get(['id'])!.value,
      details: this.editForm.get(['details'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      paymentDate: this.editForm.get(['paymentDate'])!.value
        ? moment(this.editForm.get(['paymentDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fromDate: this.editForm.get(['fromDate'])!.value,
      toDate: this.editForm.get(['toDate'])!.value,
      imageJustifContentType: this.editForm.get(['imageJustifContentType'])!.value,
      imageJustif: this.editForm.get(['imageJustif'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeePayment>>): void {
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

  trackById(index: number, item: IEmployee): any {
    return item.id;
  }
}
