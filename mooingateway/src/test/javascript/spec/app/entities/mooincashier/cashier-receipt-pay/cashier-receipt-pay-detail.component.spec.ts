import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierReceiptPayDetailComponent } from 'app/entities/mooincashier/cashier-receipt-pay/cashier-receipt-pay-detail.component';
import { CashierReceiptPay } from 'app/shared/model/mooincashier/cashier-receipt-pay.model';

describe('Component Tests', () => {
  describe('CashierReceiptPay Management Detail Component', () => {
    let comp: CashierReceiptPayDetailComponent;
    let fixture: ComponentFixture<CashierReceiptPayDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ cashierReceiptPay: new CashierReceiptPay(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierReceiptPayDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CashierReceiptPayDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CashierReceiptPayDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load cashierReceiptPay on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cashierReceiptPay).toEqual(jasmine.objectContaining({ id: 123 }));
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
