import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierReceiptItemDetailComponent } from 'app/entities/mooincashier/cashier-receipt-item/cashier-receipt-item-detail.component';
import { CashierReceiptItem } from 'app/shared/model/mooincashier/cashier-receipt-item.model';

describe('Component Tests', () => {
  describe('CashierReceiptItem Management Detail Component', () => {
    let comp: CashierReceiptItemDetailComponent;
    let fixture: ComponentFixture<CashierReceiptItemDetailComponent>;
    const route = ({ data: of({ cashierReceiptItem: new CashierReceiptItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierReceiptItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CashierReceiptItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CashierReceiptItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cashierReceiptItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cashierReceiptItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
