import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeePaymentService } from 'app/entities/mooincompanies/employee-payment/employee-payment.service';
import { IEmployeePayment, EmployeePayment } from 'app/shared/model/mooincompanies/employee-payment.model';

describe('Service Tests', () => {
  describe('EmployeePayment Service', () => {
    let injector: TestBed;
    let service: EmployeePaymentService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployeePayment;
    let expectedResult: IEmployeePayment | IEmployeePayment[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmployeePaymentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EmployeePayment(0, 'AAAAAAA', 0, currentDate, currentDate, currentDate, 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            paymentDate: currentDate.format(DATE_TIME_FORMAT),
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmployeePayment', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            paymentDate: currentDate.format(DATE_TIME_FORMAT),
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
            fromDate: currentDate,
            toDate: currentDate,
          },
          returnedFromService
        );

        service.create(new EmployeePayment()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmployeePayment', () => {
        const returnedFromService = Object.assign(
          {
            details: 'BBBBBB',
            amount: 1,
            paymentDate: currentDate.format(DATE_TIME_FORMAT),
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
            imageJustif: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
            fromDate: currentDate,
            toDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmployeePayment', () => {
        const returnedFromService = Object.assign(
          {
            details: 'BBBBBB',
            amount: 1,
            paymentDate: currentDate.format(DATE_TIME_FORMAT),
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
            imageJustif: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
            fromDate: currentDate,
            toDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EmployeePayment', () => {
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
