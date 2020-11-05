import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { EmployeePaymentDetailComponent } from 'app/entities/mooincompanies/employee-payment/employee-payment-detail.component';
import { EmployeePayment } from 'app/shared/model/mooincompanies/employee-payment.model';

describe('Component Tests', () => {
  describe('EmployeePayment Management Detail Component', () => {
    let comp: EmployeePaymentDetailComponent;
    let fixture: ComponentFixture<EmployeePaymentDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ employeePayment: new EmployeePayment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [EmployeePaymentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmployeePaymentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeePaymentDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load employeePayment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeePayment).toEqual(jasmine.objectContaining({ id: 123 }));
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
