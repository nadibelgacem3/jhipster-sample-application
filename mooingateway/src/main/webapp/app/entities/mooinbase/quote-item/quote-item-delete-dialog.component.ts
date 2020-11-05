import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuoteItem } from 'app/shared/model/mooinbase/quote-item.model';
import { QuoteItemService } from './quote-item.service';

@Component({
  templateUrl: './quote-item-delete-dialog.component.html',
})
export class QuoteItemDeleteDialogComponent {
  quoteItem?: IQuoteItem;

  constructor(protected quoteItemService: QuoteItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.quoteItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('quoteItemListModification');
      this.activeModal.close();
    });
  }
}
