import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TaxItemUpdateComponent } from 'app/entities/mooinbase/tax-item/tax-item-update.component';
import { TaxItemService } from 'app/entities/mooinbase/tax-item/tax-item.service';
import { TaxItem } from 'app/shared/model/mooinbase/tax-item.model';

describe('Component Tests', () => {
  describe('TaxItem Management Update Component', () => {
    let comp: TaxItemUpdateComponent;
    let fixture: ComponentFixture<TaxItemUpdateComponent>;
    let service: TaxItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TaxItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaxItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaxItem(123);
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
        const entity = new TaxItem();
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
