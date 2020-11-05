import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyModuleDetailComponent } from 'app/entities/mooincompanies/company-module/company-module-detail.component';
import { CompanyModule } from 'app/shared/model/mooincompanies/company-module.model';

describe('Component Tests', () => {
  describe('CompanyModule Management Detail Component', () => {
    let comp: CompanyModuleDetailComponent;
    let fixture: ComponentFixture<CompanyModuleDetailComponent>;
    const route = ({ data: of({ companyModule: new CompanyModule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyModuleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CompanyModuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyModuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load companyModule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companyModule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
