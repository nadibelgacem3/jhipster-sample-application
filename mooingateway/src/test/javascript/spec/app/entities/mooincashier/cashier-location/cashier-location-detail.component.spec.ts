import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { CashierLocationDetailComponent } from 'app/entities/mooincashier/cashier-location/cashier-location-detail.component';
import { CashierLocation } from 'app/shared/model/mooincashier/cashier-location.model';

describe('Component Tests', () => {
  describe('CashierLocation Management Detail Component', () => {
    let comp: CashierLocationDetailComponent;
    let fixture: ComponentFixture<CashierLocationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ cashierLocation: new CashierLocation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CashierLocationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CashierLocationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CashierLocationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load cashierLocation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cashierLocation).toEqual(jasmine.objectContaining({ id: 123 }));
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
