import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { BLUpdateComponent } from 'app/entities/mooinbase/bl/bl-update.component';
import { BLService } from 'app/entities/mooinbase/bl/bl.service';
import { BL } from 'app/shared/model/mooinbase/bl.model';

describe('Component Tests', () => {
  describe('BL Management Update Component', () => {
    let comp: BLUpdateComponent;
    let fixture: ComponentFixture<BLUpdateComponent>;
    let service: BLService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [BLUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BLUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BLUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BLService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BL(123);
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
        const entity = new BL();
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
