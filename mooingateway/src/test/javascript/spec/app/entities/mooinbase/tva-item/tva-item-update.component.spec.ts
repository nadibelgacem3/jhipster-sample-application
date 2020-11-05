import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TVAItemUpdateComponent } from 'app/entities/mooinbase/tva-item/tva-item-update.component';
import { TVAItemService } from 'app/entities/mooinbase/tva-item/tva-item.service';
import { TVAItem } from 'app/shared/model/mooinbase/tva-item.model';

describe('Component Tests', () => {
  describe('TVAItem Management Update Component', () => {
    let comp: TVAItemUpdateComponent;
    let fixture: ComponentFixture<TVAItemUpdateComponent>;
    let service: TVAItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TVAItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TVAItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TVAItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TVAItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TVAItem(123);
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
        const entity = new TVAItem();
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
