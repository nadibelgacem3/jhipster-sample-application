import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { ProductMarkDetailComponent } from 'app/entities/mooinbase/product-mark/product-mark-detail.component';
import { ProductMark } from 'app/shared/model/mooinbase/product-mark.model';

describe('Component Tests', () => {
  describe('ProductMark Management Detail Component', () => {
    let comp: ProductMarkDetailComponent;
    let fixture: ComponentFixture<ProductMarkDetailComponent>;
    const route = ({ data: of({ productMark: new ProductMark(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [ProductMarkDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductMarkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductMarkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productMark on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productMark).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
