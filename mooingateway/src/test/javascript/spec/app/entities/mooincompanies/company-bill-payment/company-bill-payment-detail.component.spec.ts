import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyBillPaymentDetailComponent } from 'app/entities/mooincompanies/company-bill-payment/company-bill-payment-detail.component';
import { CompanyBillPayment } from 'app/shared/model/mooincompanies/company-bill-payment.model';

describe('Component Tests', () => {
  describe('CompanyBillPayment Management Detail Component', () => {
    let comp: CompanyBillPaymentDetailComponent;
    let fixture: ComponentFixture<CompanyBillPaymentDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ companyBillPayment: new CompanyBillPayment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyBillPaymentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CompanyBillPaymentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyBillPaymentDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load companyBillPayment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companyBillPayment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
