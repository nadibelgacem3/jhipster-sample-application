import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierCostumerUpdateComponent } from 'app/entities/mooincashier/cashier-costumer/cashier-costumer-update.component';
import { CashierCostumerService } from 'app/entities/mooincashier/cashier-costumer/cashier-costumer.service';
import { CashierCostumer } from 'app/shared/model/mooincashier/cashier-costumer.model';

describe('Component Tests', () => {
  describe('CashierCostumer Management Update Component', () => {
    let comp: CashierCostumerUpdateComponent;
    let fixture: ComponentFixture<CashierCostumerUpdateComponent>;
    let service: CashierCostumerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierCostumerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CashierCostumerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CashierCostumerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CashierCostumerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CashierCostumer(123);
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
        const entity = new CashierCostumer();
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
