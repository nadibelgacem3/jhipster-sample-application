import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierReceiptItemUpdateComponent } from 'app/entities/mooincashier/cashier-receipt-item/cashier-receipt-item-update.component';
import { CashierReceiptItemService } from 'app/entities/mooincashier/cashier-receipt-item/cashier-receipt-item.service';
import { CashierReceiptItem } from 'app/shared/model/mooincashier/cashier-receipt-item.model';

describe('Component Tests', () => {
  describe('CashierReceiptItem Management Update Component', () => {
    let comp: CashierReceiptItemUpdateComponent;
    let fixture: ComponentFixture<CashierReceiptItemUpdateComponent>;
    let service: CashierReceiptItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierReceiptItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CashierReceiptItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CashierReceiptItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CashierReceiptItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CashierReceiptItem(123);
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
        const entity = new CashierReceiptItem();
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
