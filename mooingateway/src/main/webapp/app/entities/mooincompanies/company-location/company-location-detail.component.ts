import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICompanyLocation } from 'app/shared/model/mooincompanies/company-location.model';

@Component({
  selector: 'jhi-company-location-detail',
  templateUrl: './company-location-detail.component.html',
})
export class CompanyLocationDetailComponent implements OnInit {
  companyLocation: ICompanyLocation | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyLocation }) => (this.companyLocation = companyLocation));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
