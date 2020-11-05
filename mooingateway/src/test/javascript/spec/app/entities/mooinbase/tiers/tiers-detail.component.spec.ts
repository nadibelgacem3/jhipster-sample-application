import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { TiersDetailComponent } from 'app/entities/mooinbase/tiers/tiers-detail.component';
import { Tiers } from 'app/shared/model/mooinbase/tiers.model';

describe('Component Tests', () => {
  describe('Tiers Management Detail Component', () => {
    let comp: TiersDetailComponent;
    let fixture: ComponentFixture<TiersDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ tiers: new Tiers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TiersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TiersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TiersDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load tiers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tiers).toEqual(jasmine.objectContaining({ id: 123 }));
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
