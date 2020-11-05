import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { QuoteItemDetailComponent } from 'app/entities/mooinbase/quote-item/quote-item-detail.component';
import { QuoteItem } from 'app/shared/model/mooinbase/quote-item.model';

describe('Component Tests', () => {
  describe('QuoteItem Management Detail Component', () => {
    let comp: QuoteItemDetailComponent;
    let fixture: ComponentFixture<QuoteItemDetailComponent>;
    const route = ({ data: of({ quoteItem: new QuoteItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [QuoteItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuoteItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuoteItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load quoteItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.quoteItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
