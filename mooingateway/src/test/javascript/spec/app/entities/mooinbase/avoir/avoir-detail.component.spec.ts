import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { AvoirDetailComponent } from 'app/entities/mooinbase/avoir/avoir-detail.component';
import { Avoir } from 'app/shared/model/mooinbase/avoir.model';

describe('Component Tests', () => {
  describe('Avoir Management Detail Component', () => {
    let comp: AvoirDetailComponent;
    let fixture: ComponentFixture<AvoirDetailComponent>;
    const route = ({ data: of({ avoir: new Avoir(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [AvoirDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvoirDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvoirDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avoir on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avoir).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
