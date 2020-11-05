import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITiers } from 'app/shared/model/mooinbase/tiers.model';

@Component({
  selector: 'jhi-tiers-detail',
  templateUrl: './tiers-detail.component.html',
})
export class TiersDetailComponent implements OnInit {
  tiers: ITiers | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tiers }) => (this.tiers = tiers));
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
