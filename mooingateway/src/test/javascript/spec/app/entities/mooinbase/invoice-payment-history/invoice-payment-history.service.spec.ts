import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InvoicePaymentHistoryService } from 'app/entities/mooinbase/invoice-payment-history/invoice-payment-history.service';
import { IInvoicePaymentHistory, InvoicePaymentHistory } from 'app/shared/model/mooinbase/invoice-payment-history.model';
import { InvoicePaymentMethod } from 'app/shared/model/enumerations/invoice-payment-method.model';

describe('Service Tests', () => {
  describe('InvoicePaymentHistory Service', () => {
    let injector: TestBed;
    let service: InvoicePaymentHistoryService;
    let httpMock: HttpTestingController;
    let elemDefault: IInvoicePaymentHistory;
    let expectedResult: IInvoicePaymentHistory | IInvoicePaymentHistory[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InvoicePaymentHistoryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InvoicePaymentHistory(
        0,
        'AAAAAAA',
        0,
        currentDate,
        InvoicePaymentMethod.CREDIT_CARD,
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            paymentDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a InvoicePaymentHistory', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            paymentDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
          },
          returnedFromService
        );

        service.create(new InvoicePaymentHistory()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a InvoicePaymentHistory', () => {
        const returnedFromService = Object.assign(
          {
            details: 'BBBBBB',
            amount: 1,
            paymentDate: currentDate.format(DATE_TIME_FORMAT),
            paymentMethod: 'BBBBBB',
            bankCheckorTraitNumber: 'BBBBBB',
            imageJustif: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of InvoicePaymentHistory', () => {
        const returnedFromService = Object.assign(
          {
            details: 'BBBBBB',
            amount: 1,
            paymentDate: currentDate.format(DATE_TIME_FORMAT),
            paymentMethod: 'BBBBBB',
            bankCheckorTraitNumber: 'BBBBBB',
            imageJustif: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a InvoicePaymentHistory', () => {
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
