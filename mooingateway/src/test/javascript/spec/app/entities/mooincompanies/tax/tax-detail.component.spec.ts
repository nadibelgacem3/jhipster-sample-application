import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TaxDetailComponent } from 'app/entities/mooincompanies/tax/tax-detail.component';
import { Tax } from 'app/shared/model/mooincompanies/tax.model';

describe('Component Tests', () => {
  describe('Tax Management Detail Component', () => {
    let comp: TaxDetailComponent;
    let fixture: ComponentFixture<TaxDetailComponent>;
    const route = ({ data: of({ tax: new Tax(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TaxDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaxDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tax on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tax).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
