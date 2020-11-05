import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { InvoicePaymentHistoryDetailComponent } from 'app/entities/mooinbase/invoice-payment-history/invoice-payment-history-detail.component';
import { InvoicePaymentHistory } from 'app/shared/model/mooinbase/invoice-payment-history.model';

describe('Component Tests', () => {
  describe('InvoicePaymentHistory Management Detail Component', () => {
    let comp: InvoicePaymentHistoryDetailComponent;
    let fixture: ComponentFixture<InvoicePaymentHistoryDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ invoicePaymentHistory: new InvoicePaymentHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [InvoicePaymentHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InvoicePaymentHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvoicePaymentHistoryDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load invoicePaymentHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invoicePaymentHistory).toEqual(jasmine.objectContaining({ id: 123 }));
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
