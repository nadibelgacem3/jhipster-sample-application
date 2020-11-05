import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompanyBankAccount, CompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';
import { CompanyBankAccountService } from './company-bank-account.service';

@Component({
  selector: 'jhi-company-bank-account-update',
  templateUrl: './company-bank-account-update.component.html',
})
export class CompanyBankAccountUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    reference: [],
    bankName: [],
    bankAccountNumber: [],
    iban: [],
    swift: [],
    type: [],
  });

  constructor(
    protected companyBankAccountService: CompanyBankAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyBankAccount }) => {
      this.updateForm(companyBankAccount);
    });
  }

  updateForm(companyBankAccount: ICompanyBankAccount): void {
    this.editForm.patchValue({
      id: companyBankAccount.id,
      reference: companyBankAccount.reference,
      bankName: companyBankAccount.bankName,
      bankAccountNumber: companyBankAccount.bankAccountNumber,
      iban: companyBankAccount.iban,
      swift: companyBankAccount.swift,
      type: companyBankAccount.type,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyBankAccount = this.createFromForm();
    if (companyBankAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.companyBankAccountService.update(companyBankAccount));
    } else {
      this.subscribeToSaveResponse(this.companyBankAccountService.create(companyBankAccount));
    }
  }

  private createFromForm(): ICompanyBankAccount {
    return {
      ...new CompanyBankAccount(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      bankAccountNumber: this.editForm.get(['bankAccountNumber'])!.value,
      iban: this.editForm.get(['iban'])!.value,
      swift: this.editForm.get(['swift'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyBankAccount>>): void {
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
