import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TVADetailComponent } from 'app/entities/mooincompanies/tva/tva-detail.component';
import { TVA } from 'app/shared/model/mooincompanies/tva.model';

describe('Component Tests', () => {
  describe('TVA Management Detail Component', () => {
    let comp: TVADetailComponent;
    let fixture: ComponentFixture<TVADetailComponent>;
    const route = ({ data: of({ tVA: new TVA(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TVADetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TVADetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TVADetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tVA on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tVA).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
