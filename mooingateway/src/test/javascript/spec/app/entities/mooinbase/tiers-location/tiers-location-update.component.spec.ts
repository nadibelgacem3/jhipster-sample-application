import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TiersLocationUpdateComponent } from 'app/entities/mooinbase/tiers-location/tiers-location-update.component';
import { TiersLocationService } from 'app/entities/mooinbase/tiers-location/tiers-location.service';
import { TiersLocation } from 'app/shared/model/mooinbase/tiers-location.model';

describe('Component Tests', () => {
  describe('TiersLocation Management Update Component', () => {
    let comp: TiersLocationUpdateComponent;
    let fixture: ComponentFixture<TiersLocationUpdateComponent>;
    let service: TiersLocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TiersLocationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TiersLocationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TiersLocationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TiersLocationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TiersLocation(123);
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
        const entity = new TiersLocation();
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
