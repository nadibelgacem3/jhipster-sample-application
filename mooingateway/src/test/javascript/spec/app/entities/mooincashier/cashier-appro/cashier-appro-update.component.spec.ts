import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierApproUpdateComponent } from 'app/entities/mooincashier/cashier-appro/cashier-appro-update.component';
import { CashierApproService } from 'app/entities/mooincashier/cashier-appro/cashier-appro.service';
import { CashierAppro } from 'app/shared/model/mooincashier/cashier-appro.model';

describe('Component Tests', () => {
  describe('CashierAppro Management Update Component', () => {
    let comp: CashierApproUpdateComponent;
    let fixture: ComponentFixture<CashierApproUpdateComponent>;
    let service: CashierApproService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierApproUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CashierApproUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CashierApproUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CashierApproService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CashierAppro(123);
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
        const entity = new CashierAppro();
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
