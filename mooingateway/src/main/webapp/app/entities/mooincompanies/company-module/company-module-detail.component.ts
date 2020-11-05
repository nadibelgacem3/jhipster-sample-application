import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyModule } from 'app/shared/model/mooincompanies/company-module.model';

@Component({
  selector: 'jhi-company-module-detail',
  templateUrl: './company-module-detail.component.html',
})
export class CompanyModuleDetailComponent implements OnInit {
  companyModule: ICompanyModule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyModule }) => (this.companyModule = companyModule));
  }

  previousState(): void {
    window.history.back();
  }
}
