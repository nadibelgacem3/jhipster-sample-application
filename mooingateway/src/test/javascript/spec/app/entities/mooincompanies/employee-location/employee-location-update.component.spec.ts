import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { EmployeeLocationUpdateComponent } from 'app/entities/mooincompanies/employee-location/employee-location-update.component';
import { EmployeeLocationService } from 'app/entities/mooincompanies/employee-location/employee-location.service';
import { EmployeeLocation } from 'app/shared/model/mooincompanies/employee-location.model';

describe('Component Tests', () => {
  describe('EmployeeLocation Management Update Component', () => {
    let comp: EmployeeLocationUpdateComponent;
    let fixture: ComponentFixture<EmployeeLocationUpdateComponent>;
    let service: EmployeeLocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [EmployeeLocationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmployeeLocationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeLocationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeLocationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeLocation(123);
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
        const entity = new EmployeeLocation();
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
