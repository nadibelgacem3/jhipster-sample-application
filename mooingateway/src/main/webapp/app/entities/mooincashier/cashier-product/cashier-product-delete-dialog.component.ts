import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';
import { CashierProductService } from './cashier-product.service';

@Component({
  templateUrl: './cashier-product-delete-dialog.component.html',
})
export class CashierProductDeleteDialogComponent {
  cashierProduct?: ICashierProduct;

  constructor(
    protected cashierProductService: CashierProductService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cashierProductService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cashierProductListModification');
      this.activeModal.close();
    });
  }
}
