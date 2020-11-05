import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { BLDetailComponent } from 'app/entities/mooinbase/bl/bl-detail.component';
import { BL } from 'app/shared/model/mooinbase/bl.model';

describe('Component Tests', () => {
  describe('BL Management Detail Component', () => {
    let comp: BLDetailComponent;
    let fixture: ComponentFixture<BLDetailComponent>;
    const route = ({ data: of({ bL: new BL(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [BLDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BLDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BLDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bL on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bL).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
