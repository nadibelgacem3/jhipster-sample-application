import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TiersBankCheckDetailComponent } from 'app/entities/mooinbase/tiers-bank-check/tiers-bank-check-detail.component';
import { TiersBankCheck } from 'app/shared/model/mooinbase/tiers-bank-check.model';

describe('Component Tests', () => {
  describe('TiersBankCheck Management Detail Component', () => {
    let comp: TiersBankCheckDetailComponent;
    let fixture: ComponentFixture<TiersBankCheckDetailComponent>;
    const route = ({ data: of({ tiersBankCheck: new TiersBankCheck(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TiersBankCheckDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TiersBankCheckDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TiersBankCheckDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tiersBankCheck on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tiersBankCheck).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
