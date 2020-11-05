import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { OfferOrderDetailComponent } from 'app/entities/mooinecommerce/offer-order/offer-order-detail.component';
import { OfferOrder } from 'app/shared/model/mooinecommerce/offer-order.model';

describe('Component Tests', () => {
  describe('OfferOrder Management Detail Component', () => {
    let comp: OfferOrderDetailComponent;
    let fixture: ComponentFixture<OfferOrderDetailComponent>;
    const route = ({ data: of({ offerOrder: new OfferOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [OfferOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OfferOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load offerOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offerOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
