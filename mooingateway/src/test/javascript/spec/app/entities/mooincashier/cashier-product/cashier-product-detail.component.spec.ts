import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierProductDetailComponent } from 'app/entities/mooincashier/cashier-product/cashier-product-detail.component';
import { CashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';

describe('Component Tests', () => {
  describe('CashierProduct Management Detail Component', () => {
    let comp: CashierProductDetailComponent;
    let fixture: ComponentFixture<CashierProductDetailComponent>;
    const route = ({ data: of({ cashierProduct: new CashierProduct(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierProductDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CashierProductDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CashierProductDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cashierProduct on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cashierProduct).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
