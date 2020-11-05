import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TaxItemDetailComponent } from 'app/entities/mooinbase/tax-item/tax-item-detail.component';
import { TaxItem } from 'app/shared/model/mooinbase/tax-item.model';

describe('Component Tests', () => {
  describe('TaxItem Management Detail Component', () => {
    let comp: TaxItemDetailComponent;
    let fixture: ComponentFixture<TaxItemDetailComponent>;
    const route = ({ data: of({ taxItem: new TaxItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TaxItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaxItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taxItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taxItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
