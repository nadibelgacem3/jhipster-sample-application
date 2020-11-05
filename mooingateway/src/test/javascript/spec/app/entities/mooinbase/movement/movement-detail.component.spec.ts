import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { MovementDetailComponent } from 'app/entities/mooinbase/movement/movement-detail.component';
import { Movement } from 'app/shared/model/mooinbase/movement.model';

describe('Component Tests', () => {
  describe('Movement Management Detail Component', () => {
    let comp: MovementDetailComponent;
    let fixture: ComponentFixture<MovementDetailComponent>;
    const route = ({ data: of({ movement: new Movement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [MovementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MovementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MovementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load movement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.movement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
