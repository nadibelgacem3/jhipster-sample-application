import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICompanyBillPayment, CompanyBillPayment } from 'app/shared/model/mooincompanies/company-bill-payment.model';
import { CompanyBillPaymentService } from './company-bill-payment.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICompany } from 'app/shared/model/mooincompanies/company.model';
import { CompanyService } from 'app/entities/mooincompanies/company/company.service';

@Component({
  selector: 'jhi-company-bill-payment-update',
  templateUrl: './company-bill-payment-update.component.html',
})
export class CompanyBillPaymentUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];
  paymentDateDp: any;
  dateDp: any;
  dueDateDp: any;

  editForm = this.fb.group({
    id: [],
    number: [],
    details: [],
    paymentDate: [],
    paymentMethod: [],
    bankCheckorTraitNumber: [],
    imageJustif: [],
    imageJustifContentType: [],
    status: [],
    totalHT: [null, [Validators.required, Validators.min(0)]],
    totalTVA: [null, [Validators.required, Validators.min(0)]],
    totaTTC: [null, [Validators.required, Validators.min(0)]],
    date: [null, [Validators.required]],
    dueDate: [],
    withTVA: [],
    withTax: [],
    company: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected companyBillPaymentService: CompanyBillPaymentService,
    protected companyService: CompanyService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyBillPayment }) => {
      this.updateForm(companyBillPayment);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(companyBillPayment: ICompanyBillPayment): void {
    this.editForm.patchValue({
      id: companyBillPayment.id,
      number: companyBillPayment.number,
      details: companyBillPayment.details,
      paymentDate: companyBillPayment.paymentDate,
      paymentMethod: companyBillPayment.paymentMethod,
      bankCheckorTraitNumber: companyBillPayment.bankCheckorTraitNumber,
      imageJustif: companyBillPayment.imageJustif,
      imageJustifContentType: companyBillPayment.imageJustifContentType,
      status: companyBillPayment.status,
      totalHT: companyBillPayment.totalHT,
      totalTVA: companyBillPayment.totalTVA,
      totaTTC: companyBillPayment.totaTTC,
      date: companyBillPayment.date,
      dueDate: companyBillPayment.dueDate,
      withTVA: companyBillPayment.withTVA,
      withTax: companyBillPayment.withTax,
      company: companyBillPayment.company,
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
    const companyBillPayment = this.createFromForm();
    if (companyBillPayment.id !== undefined) {
      this.subscribeToSaveResponse(this.companyBillPaymentService.update(companyBillPayment));
    } else {
      this.subscribeToSaveResponse(this.companyBillPaymentService.create(companyBillPayment));
    }
  }

  private createFromForm(): ICompanyBillPayment {
    return {
      ...new CompanyBillPayment(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      details: this.editForm.get(['details'])!.value,
      paymentDate: this.editForm.get(['paymentDate'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      bankCheckorTraitNumber: this.editForm.get(['bankCheckorTraitNumber'])!.value,
      imageJustifContentType: this.editForm.get(['imageJustifContentType'])!.value,
      imageJustif: this.editForm.get(['imageJustif'])!.value,
      status: this.editForm.get(['status'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totaTTC: this.editForm.get(['totaTTC'])!.value,
      date: this.editForm.get(['date'])!.value,
      dueDate: this.editForm.get(['dueDate'])!.value,
      withTVA: this.editForm.get(['withTVA'])!.value,
      withTax: this.editForm.get(['withTax'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyBillPayment>>): void {
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
