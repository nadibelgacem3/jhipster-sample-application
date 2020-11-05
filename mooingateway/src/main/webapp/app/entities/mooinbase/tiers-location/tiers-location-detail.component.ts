import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITiersLocation } from 'app/shared/model/mooinbase/tiers-location.model';

@Component({
  selector: 'jhi-tiers-location-detail',
  templateUrl: './tiers-location-detail.component.html',
})
export class TiersLocationDetailComponent implements OnInit {
  tiersLocation: ITiersLocation | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tiersLocation }) => (this.tiersLocation = tiersLocation));
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
