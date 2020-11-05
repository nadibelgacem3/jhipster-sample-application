import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierReceiptDetailComponent } from 'app/entities/mooincashier/cashier-receipt/cashier-receipt-detail.component';
import { CashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';

describe('Component Tests', () => {
  describe('CashierReceipt Management Detail Component', () => {
    let comp: CashierReceiptDetailComponent;
    let fixture: ComponentFixture<CashierReceiptDetailComponent>;
    const route = ({ data: of({ cashierReceipt: new CashierReceipt(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierReceiptDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CashierReceiptDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CashierReceiptDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cashierReceipt on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cashierReceipt).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
