import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierReceiptPayUpdateComponent } from 'app/entities/mooincashier/cashier-receipt-pay/cashier-receipt-pay-update.component';
import { CashierReceiptPayService } from 'app/entities/mooincashier/cashier-receipt-pay/cashier-receipt-pay.service';
import { CashierReceiptPay } from 'app/shared/model/mooincashier/cashier-receipt-pay.model';

describe('Component Tests', () => {
  describe('CashierReceiptPay Management Update Component', () => {
    let comp: CashierReceiptPayUpdateComponent;
    let fixture: ComponentFixture<CashierReceiptPayUpdateComponent>;
    let service: CashierReceiptPayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierReceiptPayUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CashierReceiptPayUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CashierReceiptPayUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CashierReceiptPayService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CashierReceiptPay(123);
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
        const entity = new CashierReceiptPay();
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
