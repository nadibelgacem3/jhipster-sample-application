import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { EmployeeDetailComponent } from 'app/entities/mooincompanies/employee/employee-detail.component';
import { Employee } from 'app/shared/model/mooincompanies/employee.model';

describe('Component Tests', () => {
  describe('Employee Management Detail Component', () => {
    let comp: EmployeeDetailComponent;
    let fixture: ComponentFixture<EmployeeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ employee: new Employee(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [EmployeeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmployeeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load employee on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employee).toEqual(jasmine.objectContaining({ id: 123 }));
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
