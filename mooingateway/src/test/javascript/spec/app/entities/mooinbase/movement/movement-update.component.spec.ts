import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { MovementUpdateComponent } from 'app/entities/mooinbase/movement/movement-update.component';
import { MovementService } from 'app/entities/mooinbase/movement/movement.service';
import { Movement } from 'app/shared/model/mooinbase/movement.model';

describe('Component Tests', () => {
  describe('Movement Management Update Component', () => {
    let comp: MovementUpdateComponent;
    let fixture: ComponentFixture<MovementUpdateComponent>;
    let service: MovementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [MovementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MovementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MovementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MovementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Movement(123);
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
        const entity = new Movement();
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
