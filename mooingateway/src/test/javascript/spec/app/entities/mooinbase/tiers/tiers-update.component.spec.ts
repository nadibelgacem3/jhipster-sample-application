import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TiersUpdateComponent } from 'app/entities/mooinbase/tiers/tiers-update.component';
import { TiersService } from 'app/entities/mooinbase/tiers/tiers.service';
import { Tiers } from 'app/shared/model/mooinbase/tiers.model';

describe('Component Tests', () => {
  describe('Tiers Management Update Component', () => {
    let comp: TiersUpdateComponent;
    let fixture: ComponentFixture<TiersUpdateComponent>;
    let service: TiersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TiersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TiersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TiersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TiersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tiers(123);
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
        const entity = new Tiers();
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
