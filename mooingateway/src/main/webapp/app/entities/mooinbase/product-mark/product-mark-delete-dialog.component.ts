import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductMark } from 'app/shared/model/mooinbase/product-mark.model';
import { ProductMarkService } from './product-mark.service';

@Component({
  templateUrl: './product-mark-delete-dialog.component.html',
})
export class ProductMarkDeleteDialogComponent {
  productMark?: IProductMark;

  constructor(
    protected productMarkService: ProductMarkService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productMarkService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productMarkListModification');
      this.activeModal.close();
    });
  }
}
