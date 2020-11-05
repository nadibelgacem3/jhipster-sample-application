import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { AvoirItemDetailComponent } from 'app/entities/mooinbase/avoir-item/avoir-item-detail.component';
import { AvoirItem } from 'app/shared/model/mooinbase/avoir-item.model';

describe('Component Tests', () => {
  describe('AvoirItem Management Detail Component', () => {
    let comp: AvoirItemDetailComponent;
    let fixture: ComponentFixture<AvoirItemDetailComponent>;
    const route = ({ data: of({ avoirItem: new AvoirItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [AvoirItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvoirItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvoirItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avoirItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avoirItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
