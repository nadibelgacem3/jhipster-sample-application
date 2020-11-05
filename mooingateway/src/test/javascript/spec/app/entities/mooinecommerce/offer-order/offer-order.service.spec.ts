import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { OfferOrderService } from 'app/entities/mooinecommerce/offer-order/offer-order.service';
import { IOfferOrder, OfferOrder } from 'app/shared/model/mooinecommerce/offer-order.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';

describe('Service Tests', () => {
  describe('OfferOrder Service', () => {
    let injector: TestBed;
    let service: OfferOrderService;
    let httpMock: HttpTestingController;
    let elemDefault: IOfferOrder;
    let expectedResult: IOfferOrder | IOfferOrder[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OfferOrderService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new OfferOrder(0, 0, 0, 0, 0, currentDate, OrderStatus.DRAFT, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a OfferOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.create(new OfferOrder()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a OfferOrder', () => {
        const returnedFromService = Object.assign(
          {
            quantity: 1,
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            requesterOfferID: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of OfferOrder', () => {
        const returnedFromService = Object.assign(
          {
            quantity: 1,
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            requesterOfferID: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a OfferOrder', () => {
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
