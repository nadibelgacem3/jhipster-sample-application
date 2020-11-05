import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { AvoirUpdateComponent } from 'app/entities/mooinbase/avoir/avoir-update.component';
import { AvoirService } from 'app/entities/mooinbase/avoir/avoir.service';
import { Avoir } from 'app/shared/model/mooinbase/avoir.model';

describe('Component Tests', () => {
  describe('Avoir Management Update Component', () => {
    let comp: AvoirUpdateComponent;
    let fixture: ComponentFixture<AvoirUpdateComponent>;
    let service: AvoirService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [AvoirUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvoirUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvoirUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvoirService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Avoir(123);
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
        const entity = new Avoir();
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
