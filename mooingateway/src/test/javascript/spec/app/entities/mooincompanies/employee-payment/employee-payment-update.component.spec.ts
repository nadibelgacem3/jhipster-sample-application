import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { EmployeePaymentUpdateComponent } from 'app/entities/mooincompanies/employee-payment/employee-payment-update.component';
import { EmployeePaymentService } from 'app/entities/mooincompanies/employee-payment/employee-payment.service';
import { EmployeePayment } from 'app/shared/model/mooincompanies/employee-payment.model';

describe('Component Tests', () => {
  describe('EmployeePayment Management Update Component', () => {
    let comp: EmployeePaymentUpdateComponent;
    let fixture: ComponentFixture<EmployeePaymentUpdateComponent>;
    let service: EmployeePaymentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [EmployeePaymentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmployeePaymentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeePaymentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeePaymentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeePayment(123);
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
        const entity = new EmployeePayment();
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
