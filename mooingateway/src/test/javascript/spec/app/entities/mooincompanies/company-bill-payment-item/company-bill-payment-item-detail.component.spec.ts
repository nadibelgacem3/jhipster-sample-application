import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyBillPaymentItemDetailComponent } from 'app/entities/mooincompanies/company-bill-payment-item/company-bill-payment-item-detail.component';
import { CompanyBillPaymentItem } from 'app/shared/model/mooincompanies/company-bill-payment-item.model';

describe('Component Tests', () => {
  describe('CompanyBillPaymentItem Management Detail Component', () => {
    let comp: CompanyBillPaymentItemDetailComponent;
    let fixture: ComponentFixture<CompanyBillPaymentItemDetailComponent>;
    const route = ({ data: of({ companyBillPaymentItem: new CompanyBillPaymentItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyBillPaymentItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CompanyBillPaymentItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyBillPaymentItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load companyBillPaymentItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companyBillPaymentItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
