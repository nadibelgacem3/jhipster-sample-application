import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { MockEventManager } from '../../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../../helpers/mock-active-modal.service';
import { CompanyBillPaymentItemDeleteDialogComponent } from 'app/entities/mooincompanies/company-bill-payment-item/company-bill-payment-item-delete-dialog.component';
import { CompanyBillPaymentItemService } from 'app/entities/mooincompanies/company-bill-payment-item/company-bill-payment-item.service';

describe('Component Tests', () => {
  describe('CompanyBillPaymentItem Management Delete Component', () => {
    let comp: CompanyBillPaymentItemDeleteDialogComponent;
    let fixture: ComponentFixture<CompanyBillPaymentItemDeleteDialogComponent>;
    let service: CompanyBillPaymentItemService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyBillPaymentItemDeleteDialogComponent],
      })
        .overrideTemplate(CompanyBillPaymentItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyBillPaymentItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyBillPaymentItemService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
