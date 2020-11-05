import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TransactionCompUpdateComponent } from 'app/entities/mooincompanies/transaction-comp/transaction-comp-update.component';
import { TransactionCompService } from 'app/entities/mooincompanies/transaction-comp/transaction-comp.service';
import { TransactionComp } from 'app/shared/model/mooincompanies/transaction-comp.model';

describe('Component Tests', () => {
  describe('TransactionComp Management Update Component', () => {
    let comp: TransactionCompUpdateComponent;
    let fixture: ComponentFixture<TransactionCompUpdateComponent>;
    let service: TransactionCompService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TransactionCompUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TransactionCompUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionCompUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionCompService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionComp(123);
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
        const entity = new TransactionComp();
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
