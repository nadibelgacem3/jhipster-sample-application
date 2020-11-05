import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITiersBankCheck } from 'app/shared/model/mooinbase/tiers-bank-check.model';

@Component({
  selector: 'jhi-tiers-bank-check-detail',
  templateUrl: './tiers-bank-check-detail.component.html',
})
export class TiersBankCheckDetailComponent implements OnInit {
  tiersBankCheck: ITiersBankCheck | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tiersBankCheck }) => (this.tiersBankCheck = tiersBankCheck));
  }

  previousState(): void {
    window.history.back();
  }
}
