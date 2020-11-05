import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { TiersLocationDetailComponent } from 'app/entities/mooinbase/tiers-location/tiers-location-detail.component';
import { TiersLocation } from 'app/shared/model/mooinbase/tiers-location.model';

describe('Component Tests', () => {
  describe('TiersLocation Management Detail Component', () => {
    let comp: TiersLocationDetailComponent;
    let fixture: ComponentFixture<TiersLocationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ tiersLocation: new TiersLocation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TiersLocationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TiersLocationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TiersLocationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load tiersLocation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tiersLocation).toEqual(jasmine.objectContaining({ id: 123 }));
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
