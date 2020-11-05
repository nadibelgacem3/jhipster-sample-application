import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICompany, Company } from 'app/shared/model/mooincompanies/company.model';
import { CompanyService } from './company.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';
import { CompanyBankAccountService } from 'app/entities/mooincompanies/company-bank-account/company-bank-account.service';
import { ICompanyLocation } from 'app/shared/model/mooincompanies/company-location.model';
import { CompanyLocationService } from 'app/entities/mooincompanies/company-location/company-location.service';

type SelectableEntity = ICompanyBankAccount | ICompanyLocation;

@Component({
  selector: 'jhi-company-update',
  templateUrl: './company-update.component.html',
})
export class CompanyUpdateComponent implements OnInit {
  isSaving = false;
  companybankaccounts: ICompanyBankAccount[] = [];
  companylocations: ICompanyLocation[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(4)]],
    logo: [],
    logoContentType: [],
    phone1: [],
    phone2: [],
    fax: [],
    email1: [null, [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    email2: [null, [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    description: [],
    businessType: [],
    currencyUnit: [],
    currencyQuotient: [],
    commercialRegister: [],
    isActivated: [],
    themeColor: [],
    facebook: [],
    linkedin: [],
    twitter: [],
    instagram: [],
    companyBankAccount: [],
    companyLocation: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected companyService: CompanyService,
    protected companyBankAccountService: CompanyBankAccountService,
    protected companyLocationService: CompanyLocationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ company }) => {
      this.updateForm(company);

      this.companyBankAccountService
        .query({ filter: 'company-is-null' })
        .pipe(
          map((res: HttpResponse<ICompanyBankAccount[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICompanyBankAccount[]) => {
          if (!company.companyBankAccount || !company.companyBankAccount.id) {
            this.companybankaccounts = resBody;
          } else {
            this.companyBankAccountService
              .find(company.companyBankAccount.id)
              .pipe(
                map((subRes: HttpResponse<ICompanyBankAccount>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICompanyBankAccount[]) => (this.companybankaccounts = concatRes));
          }
        });

      this.companyLocationService
        .query({ filter: 'company-is-null' })
        .pipe(
          map((res: HttpResponse<ICompanyLocation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICompanyLocation[]) => {
          if (!company.companyLocation || !company.companyLocation.id) {
            this.companylocations = resBody;
          } else {
            this.companyLocationService
              .find(company.companyLocation.id)
              .pipe(
                map((subRes: HttpResponse<ICompanyLocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICompanyLocation[]) => (this.companylocations = concatRes));
          }
        });
    });
  }

  updateForm(company: ICompany): void {
    this.editForm.patchValue({
      id: company.id,
      name: company.name,
      logo: company.logo,
      logoContentType: company.logoContentType,
      phone1: company.phone1,
      phone2: company.phone2,
      fax: company.fax,
      email1: company.email1,
      email2: company.email2,
      description: company.description,
      businessType: company.businessType,
      currencyUnit: company.currencyUnit,
      currencyQuotient: company.currencyQuotient,
      commercialRegister: company.commercialRegister,
      isActivated: company.isActivated,
      themeColor: company.themeColor,
      facebook: company.facebook,
      linkedin: company.linkedin,
      twitter: company.twitter,
      instagram: company.instagram,
      companyBankAccount: company.companyBankAccount,
      companyLocation: company.companyLocation,
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
    const company = this.createFromForm();
    if (company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  private createFromForm(): ICompany {
    return {
      ...new Company(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      logoContentType: this.editForm.get(['logoContentType'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      phone1: this.editForm.get(['phone1'])!.value,
      phone2: this.editForm.get(['phone2'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      email1: this.editForm.get(['email1'])!.value,
      email2: this.editForm.get(['email2'])!.value,
      description: this.editForm.get(['description'])!.value,
      businessType: this.editForm.get(['businessType'])!.value,
      currencyUnit: this.editForm.get(['currencyUnit'])!.value,
      currencyQuotient: this.editForm.get(['currencyQuotient'])!.value,
      commercialRegister: this.editForm.get(['commercialRegister'])!.value,
      isActivated: this.editForm.get(['isActivated'])!.value,
      themeColor: this.editForm.get(['themeColor'])!.value,
      facebook: this.editForm.get(['facebook'])!.value,
      linkedin: this.editForm.get(['linkedin'])!.value,
      twitter: this.editForm.get(['twitter'])!.value,
      instagram: this.editForm.get(['instagram'])!.value,
      companyBankAccount: this.editForm.get(['companyBankAccount'])!.value,
      companyLocation: this.editForm.get(['companyLocation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>): void {
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
