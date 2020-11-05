import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierApproDetailComponent } from 'app/entities/mooincashier/cashier-appro/cashier-appro-detail.component';
import { CashierAppro } from 'app/shared/model/mooincashier/cashier-appro.model';

describe('Component Tests', () => {
  describe('CashierAppro Management Detail Component', () => {
    let comp: CashierApproDetailComponent;
    let fixture: ComponentFixture<CashierApproDetailComponent>;
    const route = ({ data: of({ cashierAppro: new CashierAppro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierApproDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CashierApproDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CashierApproDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cashierAppro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cashierAppro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
