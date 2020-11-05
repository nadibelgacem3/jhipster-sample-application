import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyBillPaymentItemComponent } from 'app/entities/mooincompanies/company-bill-payment-item/company-bill-payment-item.component';
import { CompanyBillPaymentItemService } from 'app/entities/mooincompanies/company-bill-payment-item/company-bill-payment-item.service';
import { CompanyBillPaymentItem } from 'app/shared/model/mooincompanies/company-bill-payment-item.model';

describe('Component Tests', () => {
  describe('CompanyBillPaymentItem Management Component', () => {
    let comp: CompanyBillPaymentItemComponent;
    let fixture: ComponentFixture<CompanyBillPaymentItemComponent>;
    let service: CompanyBillPaymentItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyBillPaymentItemComponent],
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
        .overrideTemplate(CompanyBillPaymentItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyBillPaymentItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyBillPaymentItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompanyBillPaymentItem(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.companyBillPaymentItems && comp.companyBillPaymentItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompanyBillPaymentItem(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.companyBillPaymentItems && comp.companyBillPaymentItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
