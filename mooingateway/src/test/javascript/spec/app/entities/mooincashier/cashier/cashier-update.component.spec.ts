import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierUpdateComponent } from 'app/entities/mooincashier/cashier/cashier-update.component';
import { CashierService } from 'app/entities/mooincashier/cashier/cashier.service';
import { Cashier } from 'app/shared/model/mooincashier/cashier.model';

describe('Component Tests', () => {
  describe('Cashier Management Update Component', () => {
    let comp: CashierUpdateComponent;
    let fixture: ComponentFixture<CashierUpdateComponent>;
    let service: CashierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CashierUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CashierUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CashierService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cashier(123);
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
        const entity = new Cashier();
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
