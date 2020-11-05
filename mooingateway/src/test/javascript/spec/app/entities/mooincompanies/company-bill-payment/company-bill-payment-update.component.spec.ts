import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyBillPaymentUpdateComponent } from 'app/entities/mooincompanies/company-bill-payment/company-bill-payment-update.component';
import { CompanyBillPaymentService } from 'app/entities/mooincompanies/company-bill-payment/company-bill-payment.service';
import { CompanyBillPayment } from 'app/shared/model/mooincompanies/company-bill-payment.model';

describe('Component Tests', () => {
  describe('CompanyBillPayment Management Update Component', () => {
    let comp: CompanyBillPaymentUpdateComponent;
    let fixture: ComponentFixture<CompanyBillPaymentUpdateComponent>;
    let service: CompanyBillPaymentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyBillPaymentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CompanyBillPaymentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyBillPaymentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyBillPaymentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompanyBillPayment(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompanyBillPayment();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
