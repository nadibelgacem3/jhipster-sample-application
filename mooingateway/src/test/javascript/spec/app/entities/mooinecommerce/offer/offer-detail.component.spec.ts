import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MooingatewayTestModule } from '../../../../test.module';
import { OfferDetailComponent } from 'app/entities/mooinecommerce/offer/offer-detail.component';
import { Offer } from 'app/shared/model/mooinecommerce/offer.model';

describe('Component Tests', () => {
  describe('Offer Management Detail Component', () => {
    let comp: OfferDetailComponent;
    let fixture: ComponentFixture<OfferDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ offer: new Offer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [OfferDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OfferDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load offer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offer).toEqual(jasmine.objectContaining({ id: 123 }));
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
