import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEmployee, Employee } from 'app/shared/model/mooincompanies/employee.model';
import { EmployeeService } from './employee.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployeeLocation } from 'app/shared/model/mooincompanies/employee-location.model';
import { EmployeeLocationService } from 'app/entities/mooincompanies/employee-location/employee-location.service';
import { ICompany } from 'app/shared/model/mooincompanies/company.model';
import { CompanyService } from 'app/entities/mooincompanies/company/company.service';

type SelectableEntity = IEmployeeLocation | ICompany;

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  employeelocations: IEmployeeLocation[] = [];
  companies: ICompany[] = [];
  dateOfborthDp: any;
  dateOfRecruitmentDp: any;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required, Validators.minLength(3)]],
    lastName: [null, [Validators.required, Validators.minLength(3)]],
    jobTitle: [],
    gender: [],
    dateOfborth: [],
    dateOfRecruitment: [],
    image: [],
    imageContentType: [],
    email: [null, [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    phoneNumber: [],
    salary: [],
    commissionPct: [],
    rates: [],
    employeeLocation: [],
    company: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected employeeService: EmployeeService,
    protected employeeLocationService: EmployeeLocationService,
    protected companyService: CompanyService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);

      this.employeeLocationService
        .query({ filter: 'employee-is-null' })
        .pipe(
          map((res: HttpResponse<IEmployeeLocation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEmployeeLocation[]) => {
          if (!employee.employeeLocation || !employee.employeeLocation.id) {
            this.employeelocations = resBody;
          } else {
            this.employeeLocationService
              .find(employee.employeeLocation.id)
              .pipe(
                map((subRes: HttpResponse<IEmployeeLocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEmployeeLocation[]) => (this.employeelocations = concatRes));
          }
        });

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      firstName: employee.firstName,
      lastName: employee.lastName,
      jobTitle: employee.jobTitle,
      gender: employee.gender,
      dateOfborth: employee.dateOfborth,
      dateOfRecruitment: employee.dateOfRecruitment,
      image: employee.image,
      imageContentType: employee.imageContentType,
      email: employee.email,
      phoneNumber: employee.phoneNumber,
      salary: employee.salary,
      commissionPct: employee.commissionPct,
      rates: employee.rates,
      employeeLocation: employee.employeeLocation,
      company: employee.company,
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
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      jobTitle: this.editForm.get(['jobTitle'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      dateOfborth: this.editForm.get(['dateOfborth'])!.value,
      dateOfRecruitment: this.editForm.get(['dateOfRecruitment'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      salary: this.editForm.get(['salary'])!.value,
      commissionPct: this.editForm.get(['commissionPct'])!.value,
      rates: this.editForm.get(['rates'])!.value,
      employeeLocation: this.editForm.get(['employeeLocation'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
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
