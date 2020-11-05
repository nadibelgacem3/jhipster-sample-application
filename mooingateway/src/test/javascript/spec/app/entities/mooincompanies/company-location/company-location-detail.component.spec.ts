import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyLocationDetailComponent } from 'app/entities/mooincompanies/company-location/company-location-detail.component';
import { CompanyLocation } from 'app/shared/model/mooincompanies/company-location.model';

describe('Component Tests', () => {
  describe('CompanyLocation Management Detail Component', () => {
    let comp: CompanyLocationDetailComponent;
    let fixture: ComponentFixture<CompanyLocationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ companyLocation: new CompanyLocation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyLocationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CompanyLocationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyLocationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load companyLocation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companyLocation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
