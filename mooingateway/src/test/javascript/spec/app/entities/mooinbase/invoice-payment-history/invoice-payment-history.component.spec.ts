import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { MooingatewayTestModule } from '../../../../test.module';
import { InvoicePaymentHistoryComponent } from 'app/entities/mooinbase/invoice-payment-history/invoice-payment-history.component';
import { InvoicePaymentHistoryService } from 'app/entities/mooinbase/invoice-payment-history/invoice-payment-history.service';
import { InvoicePaymentHistory } from 'app/shared/model/mooinbase/invoice-payment-history.model';

describe('Component Tests', () => {
  describe('InvoicePaymentHistory Management Component', () => {
    let comp: InvoicePaymentHistoryComponent;
    let fixture: ComponentFixture<InvoicePaymentHistoryComponent>;
    let service: InvoicePaymentHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [InvoicePaymentHistoryComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(InvoicePaymentHistoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoicePaymentHistoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoicePaymentHistoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InvoicePaymentHistory(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.invoicePaymentHistories && comp.invoicePaymentHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InvoicePaymentHistory(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.invoicePaymentHistories && comp.invoicePaymentHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
