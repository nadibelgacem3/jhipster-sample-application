import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';
import { CompanyBankAccountService } from './company-bank-account.service';

@Component({
  templateUrl: './company-bank-account-delete-dialog.component.html',
})
export class CompanyBankAccountDeleteDialogComponent {
  companyBankAccount?: ICompanyBankAccount;

  constructor(
    protected companyBankAccountService: CompanyBankAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyBankAccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('companyBankAccountListModification');
      this.activeModal.close();
    });
  }
}
