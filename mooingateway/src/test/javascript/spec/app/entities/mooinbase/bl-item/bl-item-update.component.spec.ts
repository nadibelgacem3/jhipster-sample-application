import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { BLItemUpdateComponent } from 'app/entities/mooinbase/bl-item/bl-item-update.component';
import { BLItemService } from 'app/entities/mooinbase/bl-item/bl-item.service';
import { BLItem } from 'app/shared/model/mooinbase/bl-item.model';

describe('Component Tests', () => {
  describe('BLItem Management Update Component', () => {
    let comp: BLItemUpdateComponent;
    let fixture: ComponentFixture<BLItemUpdateComponent>;
    let service: BLItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [BLItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BLItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BLItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BLItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BLItem(123);
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
        const entity = new BLItem();
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
