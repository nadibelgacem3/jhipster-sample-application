import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMovement } from 'app/shared/model/mooinbase/movement.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MovementService } from './movement.service';
import { MovementDeleteDialogComponent } from './movement-delete-dialog.component';

@Component({
  selector: 'jhi-movement',
  templateUrl: './movement.component.html',
})
export class MovementComponent implements OnInit, OnDestroy {
  movements: IMovement[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected movementService: MovementService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.movements = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.movementService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IMovement[]>) => this.paginateMovements(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.movements = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMovements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMovement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMovements(): void {
    this.eventSubscriber = this.eventManager.subscribe('movementListModification', () => this.reset());
  }

  delete(movement: IMovement): void {
    const modalRef = this.modalService.open(MovementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.movement = movement;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMovements(data: IMovement[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.movements.push(data[i]);
      }
    }
  }
}
