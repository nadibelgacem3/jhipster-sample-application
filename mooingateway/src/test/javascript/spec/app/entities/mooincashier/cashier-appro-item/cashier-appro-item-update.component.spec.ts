import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierApproItemUpdateComponent } from 'app/entities/mooincashier/cashier-appro-item/cashier-appro-item-update.component';
import { CashierApproItemService } from 'app/entities/mooincashier/cashier-appro-item/cashier-appro-item.service';
import { CashierApproItem } from 'app/shared/model/mooincashier/cashier-appro-item.model';

describe('Component Tests', () => {
  describe('CashierApproItem Management Update Component', () => {
    let comp: CashierApproItemUpdateComponent;
    let fixture: ComponentFixture<CashierApproItemUpdateComponent>;
    let service: CashierApproItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierApproItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CashierApproItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CashierApproItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CashierApproItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CashierApproItem(123);
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
        const entity = new CashierApproItem();
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
