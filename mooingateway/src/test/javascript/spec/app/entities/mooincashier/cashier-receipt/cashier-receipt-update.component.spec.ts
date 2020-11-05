import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierReceiptUpdateComponent } from 'app/entities/mooincashier/cashier-receipt/cashier-receipt-update.component';
import { CashierReceiptService } from 'app/entities/mooincashier/cashier-receipt/cashier-receipt.service';
import { CashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';

describe('Component Tests', () => {
  describe('CashierReceipt Management Update Component', () => {
    let comp: CashierReceiptUpdateComponent;
    let fixture: ComponentFixture<CashierReceiptUpdateComponent>;
    let service: CashierReceiptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierReceiptUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CashierReceiptUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CashierReceiptUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CashierReceiptService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CashierReceipt(123);
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
        const entity = new CashierReceipt();
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
