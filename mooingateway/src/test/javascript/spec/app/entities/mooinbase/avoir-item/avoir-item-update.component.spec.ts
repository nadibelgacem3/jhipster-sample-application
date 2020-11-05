import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { AvoirItemUpdateComponent } from 'app/entities/mooinbase/avoir-item/avoir-item-update.component';
import { AvoirItemService } from 'app/entities/mooinbase/avoir-item/avoir-item.service';
import { AvoirItem } from 'app/shared/model/mooinbase/avoir-item.model';

describe('Component Tests', () => {
  describe('AvoirItem Management Update Component', () => {
    let comp: AvoirItemUpdateComponent;
    let fixture: ComponentFixture<AvoirItemUpdateComponent>;
    let service: AvoirItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [AvoirItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvoirItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvoirItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvoirItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvoirItem(123);
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
        const entity = new AvoirItem();
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
