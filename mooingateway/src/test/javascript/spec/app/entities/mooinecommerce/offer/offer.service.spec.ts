import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { OfferService } from 'app/entities/mooinecommerce/offer/offer.service';
import { IOffer, Offer } from 'app/shared/model/mooinecommerce/offer.model';
import { OfferStatus } from 'app/shared/model/enumerations/offer-status.model';

describe('Service Tests', () => {
  describe('Offer Service', () => {
    let injector: TestBed;
    let service: OfferService;
    let httpMock: HttpTestingController;
    let elemDefault: IOffer;
    let expectedResult: IOffer | IOffer[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OfferService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Offer(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        OfferStatus.AVAILABLE,
        0,
        0,
        0,
        0,
        false,
        false,
        false,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateBegin: currentDate.format(DATE_FORMAT),
            dateEnd: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Offer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateBegin: currentDate.format(DATE_FORMAT),
            dateEnd: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateBegin: currentDate,
            dateEnd: currentDate,
          },
          returnedFromService
        );

        service.create(new Offer()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Offer', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            image: 'BBBBBB',
            dateBegin: currentDate.format(DATE_FORMAT),
            dateEnd: currentDate.format(DATE_FORMAT),
            duration: 'BBBBBB',
            status: 'BBBBBB',
            productId: 1,
            saleUnitPriceBeforeDiscount: 1,
            discountRate: 1,
            saleUnitPriceAfterDiscount: 1,
            withTVA: true,
            withTax: true,
            isActivated: true,
            companyID: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateBegin: currentDate,
            dateEnd: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Offer', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            image: 'BBBBBB',
            dateBegin: currentDate.format(DATE_FORMAT),
            dateEnd: currentDate.format(DATE_FORMAT),
            duration: 'BBBBBB',
            status: 'BBBBBB',
            productId: 1,
            saleUnitPriceBeforeDiscount: 1,
            discountRate: 1,
            saleUnitPriceAfterDiscount: 1,
            withTVA: true,
            withTax: true,
            isActivated: true,
            companyID: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateBegin: currentDate,
            dateEnd: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Offer', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
