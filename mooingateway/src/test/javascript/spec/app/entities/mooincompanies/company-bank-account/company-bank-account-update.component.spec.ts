import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyBankAccountUpdateComponent } from 'app/entities/mooincompanies/company-bank-account/company-bank-account-update.component';
import { CompanyBankAccountService } from 'app/entities/mooincompanies/company-bank-account/company-bank-account.service';
import { CompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';

describe('Component Tests', () => {
  describe('CompanyBankAccount Management Update Component', () => {
    let comp: CompanyBankAccountUpdateComponent;
    let fixture: ComponentFixture<CompanyBankAccountUpdateComponent>;
    let service: CompanyBankAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyBankAccountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CompanyBankAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyBankAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyBankAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompanyBankAccount(123);
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
        const entity = new CompanyBankAccount();
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
