import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { EmployeeLocationDetailComponent } from 'app/entities/mooincompanies/employee-location/employee-location-detail.component';
import { EmployeeLocation } from 'app/shared/model/mooincompanies/employee-location.model';

describe('Component Tests', () => {
  describe('EmployeeLocation Management Detail Component', () => {
    let comp: EmployeeLocationDetailComponent;
    let fixture: ComponentFixture<EmployeeLocationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ employeeLocation: new EmployeeLocation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [EmployeeLocationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmployeeLocationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeLocationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load employeeLocation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeLocation).toEqual(jasmine.objectContaining({ id: 123 }));
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
