import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TiersBankCheckUpdateComponent } from 'app/entities/mooinbase/tiers-bank-check/tiers-bank-check-update.component';
import { TiersBankCheckService } from 'app/entities/mooinbase/tiers-bank-check/tiers-bank-check.service';
import { TiersBankCheck } from 'app/shared/model/mooinbase/tiers-bank-check.model';

describe('Component Tests', () => {
  describe('TiersBankCheck Management Update Component', () => {
    let comp: TiersBankCheckUpdateComponent;
    let fixture: ComponentFixture<TiersBankCheckUpdateComponent>;
    let service: TiersBankCheckService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TiersBankCheckUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TiersBankCheckUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TiersBankCheckUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TiersBankCheckService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TiersBankCheck(123);
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
        const entity = new TiersBankCheck();
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
