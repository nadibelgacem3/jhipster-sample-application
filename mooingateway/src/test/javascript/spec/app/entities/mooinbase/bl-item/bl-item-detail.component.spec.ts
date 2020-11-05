import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { BLItemDetailComponent } from 'app/entities/mooinbase/bl-item/bl-item-detail.component';
import { BLItem } from 'app/shared/model/mooinbase/bl-item.model';

describe('Component Tests', () => {
  describe('BLItem Management Detail Component', () => {
    let comp: BLItemDetailComponent;
    let fixture: ComponentFixture<BLItemDetailComponent>;
    const route = ({ data: of({ bLItem: new BLItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [BLItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BLItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BLItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bLItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bLItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
