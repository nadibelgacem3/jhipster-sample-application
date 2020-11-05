import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyBillPaymentItemUpdateComponent } from 'app/entities/mooincompanies/company-bill-payment-item/company-bill-payment-item-update.component';
import { CompanyBillPaymentItemService } from 'app/entities/mooincompanies/company-bill-payment-item/company-bill-payment-item.service';
import { CompanyBillPaymentItem } from 'app/shared/model/mooincompanies/company-bill-payment-item.model';

describe('Component Tests', () => {
  describe('CompanyBillPaymentItem Management Update Component', () => {
    let comp: CompanyBillPaymentItemUpdateComponent;
    let fixture: ComponentFixture<CompanyBillPaymentItemUpdateComponent>;
    let service: CompanyBillPaymentItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyBillPaymentItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CompanyBillPaymentItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyBillPaymentItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyBillPaymentItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompanyBillPaymentItem(123);
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
        const entity = new CompanyBillPaymentItem();
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
