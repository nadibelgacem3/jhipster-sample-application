import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { ProductMarkUpdateComponent } from 'app/entities/mooinbase/product-mark/product-mark-update.component';
import { ProductMarkService } from 'app/entities/mooinbase/product-mark/product-mark.service';
import { ProductMark } from 'app/shared/model/mooinbase/product-mark.model';

describe('Component Tests', () => {
  describe('ProductMark Management Update Component', () => {
    let comp: ProductMarkUpdateComponent;
    let fixture: ComponentFixture<ProductMarkUpdateComponent>;
    let service: ProductMarkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [ProductMarkUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductMarkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductMarkUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductMarkService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductMark(123);
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
        const entity = new ProductMark();
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
