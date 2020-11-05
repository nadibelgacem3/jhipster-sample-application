import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TVAItemDetailComponent } from 'app/entities/mooinbase/tva-item/tva-item-detail.component';
import { TVAItem } from 'app/shared/model/mooinbase/tva-item.model';

describe('Component Tests', () => {
  describe('TVAItem Management Detail Component', () => {
    let comp: TVAItemDetailComponent;
    let fixture: ComponentFixture<TVAItemDetailComponent>;
    const route = ({ data: of({ tVAItem: new TVAItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TVAItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TVAItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TVAItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tVAItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tVAItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
