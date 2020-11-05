import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierApproItemDetailComponent } from 'app/entities/mooincashier/cashier-appro-item/cashier-appro-item-detail.component';
import { CashierApproItem } from 'app/shared/model/mooincashier/cashier-appro-item.model';

describe('Component Tests', () => {
  describe('CashierApproItem Management Detail Component', () => {
    let comp: CashierApproItemDetailComponent;
    let fixture: ComponentFixture<CashierApproItemDetailComponent>;
    const route = ({ data: of({ cashierApproItem: new CashierApproItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierApproItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CashierApproItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CashierApproItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cashierApproItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cashierApproItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
