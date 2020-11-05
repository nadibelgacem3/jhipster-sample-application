import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionComp } from 'app/shared/model/mooincompanies/transaction-comp.model';

@Component({
  selector: 'jhi-transaction-comp-detail',
  templateUrl: './transaction-comp-detail.component.html',
})
export class TransactionCompDetailComponent implements OnInit {
  transactionComp: ITransactionComp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionComp }) => (this.transactionComp = transactionComp));
  }

  previousState(): void {
    window.history.back();
  }
}
