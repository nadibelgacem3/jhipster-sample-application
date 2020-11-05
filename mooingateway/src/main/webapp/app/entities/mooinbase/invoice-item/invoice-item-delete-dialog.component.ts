import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoiceItem } from 'app/shared/model/mooinbase/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';

@Component({
  templateUrl: './invoice-item-delete-dialog.component.html',
})
export class InvoiceItemDeleteDialogComponent {
  invoiceItem?: IInvoiceItem;

  constructor(
    protected invoiceItemService: InvoiceItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invoiceItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('invoiceItemListModification');
      this.activeModal.close();
    });
  }
}
