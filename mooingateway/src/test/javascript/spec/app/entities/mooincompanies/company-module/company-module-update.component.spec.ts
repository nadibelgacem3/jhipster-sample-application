import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyModuleUpdateComponent } from 'app/entities/mooincompanies/company-module/company-module-update.component';
import { CompanyModuleService } from 'app/entities/mooincompanies/company-module/company-module.service';
import { CompanyModule } from 'app/shared/model/mooincompanies/company-module.model';

describe('Component Tests', () => {
  describe('CompanyModule Management Update Component', () => {
    let comp: CompanyModuleUpdateComponent;
    let fixture: ComponentFixture<CompanyModuleUpdateComponent>;
    let service: CompanyModuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyModuleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CompanyModuleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyModuleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyModuleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompanyModule(123);
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
        const entity = new CompanyModule();
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
