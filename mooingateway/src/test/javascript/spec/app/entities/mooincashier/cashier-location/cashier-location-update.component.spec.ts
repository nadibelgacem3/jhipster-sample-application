import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierLocationUpdateComponent } from 'app/entities/mooincashier/cashier-location/cashier-location-update.component';
import { CashierLocationService } from 'app/entities/mooincashier/cashier-location/cashier-location.service';
import { CashierLocation } from 'app/shared/model/mooincashier/cashier-location.model';

describe('Component Tests', () => {
  describe('CashierLocation Management Update Component', () => {
    let comp: CashierLocationUpdateComponent;
    let fixture: ComponentFixture<CashierLocationUpdateComponent>;
    let service: CashierLocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierLocationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CashierLocationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CashierLocationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CashierLocationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CashierLocation(123);
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
        const entity = new CashierLocation();
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
