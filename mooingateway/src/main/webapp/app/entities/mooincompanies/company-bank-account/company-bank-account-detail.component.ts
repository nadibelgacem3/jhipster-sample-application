import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';

@Component({
  selector: 'jhi-company-bank-account-detail',
  templateUrl: './company-bank-account-detail.component.html',
})
export class CompanyBankAccountDetailComponent implements OnInit {
  companyBankAccount: ICompanyBankAccount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyBankAccount }) => (this.companyBankAccount = companyBankAccount));
  }

  previousState(): void {
    window.history.back();
  }
}
