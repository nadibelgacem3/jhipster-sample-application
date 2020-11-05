import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { InvoicePaymentHistoryUpdateComponent } from 'app/entities/mooinbase/invoice-payment-history/invoice-payment-history-update.component';
import { InvoicePaymentHistoryService } from 'app/entities/mooinbase/invoice-payment-history/invoice-payment-history.service';
import { InvoicePaymentHistory } from 'app/shared/model/mooinbase/invoice-payment-history.model';

describe('Component Tests', () => {
  describe('InvoicePaymentHistory Management Update Component', () => {
    let comp: InvoicePaymentHistoryUpdateComponent;
    let fixture: ComponentFixture<InvoicePaymentHistoryUpdateComponent>;
    let service: InvoicePaymentHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [InvoicePaymentHistoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InvoicePaymentHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoicePaymentHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoicePaymentHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InvoicePaymentHistory(123);
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
        const entity = new InvoicePaymentHistory();
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
