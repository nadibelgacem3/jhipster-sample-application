import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { QuoteItemUpdateComponent } from 'app/entities/mooinbase/quote-item/quote-item-update.component';
import { QuoteItemService } from 'app/entities/mooinbase/quote-item/quote-item.service';
import { QuoteItem } from 'app/shared/model/mooinbase/quote-item.model';

describe('Component Tests', () => {
  describe('QuoteItem Management Update Component', () => {
    let comp: QuoteItemUpdateComponent;
    let fixture: ComponentFixture<QuoteItemUpdateComponent>;
    let service: QuoteItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [QuoteItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuoteItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuoteItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuoteItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuoteItem(123);
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
        const entity = new QuoteItem();
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
