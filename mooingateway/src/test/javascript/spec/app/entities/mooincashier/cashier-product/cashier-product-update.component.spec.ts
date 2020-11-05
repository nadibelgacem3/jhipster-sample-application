import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierProductUpdateComponent } from 'app/entities/mooincashier/cashier-product/cashier-product-update.component';
import { CashierProductService } from 'app/entities/mooincashier/cashier-product/cashier-product.service';
import { CashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';

describe('Component Tests', () => {
  describe('CashierProduct Management Update Component', () => {
    let comp: CashierProductUpdateComponent;
    let fixture: ComponentFixture<CashierProductUpdateComponent>;
    let service: CashierProductService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierProductUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CashierProductUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CashierProductUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CashierProductService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CashierProduct(123);
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
        const entity = new CashierProduct();
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
