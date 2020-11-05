import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CompanyBillPaymentService } from 'app/entities/mooincompanies/company-bill-payment/company-bill-payment.service';
import { ICompanyBillPayment, CompanyBillPayment } from 'app/shared/model/mooincompanies/company-bill-payment.model';
import { CompanyPaymentMethod } from 'app/shared/model/enumerations/company-payment-method.model';
import { CompanyModulePaymentStatus } from 'app/shared/model/enumerations/company-module-payment-status.model';

describe('Service Tests', () => {
  describe('CompanyBillPayment Service', () => {
    let injector: TestBed;
    let service: CompanyBillPaymentService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompanyBillPayment;
    let expectedResult: ICompanyBillPayment | ICompanyBillPayment[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CompanyBillPaymentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CompanyBillPayment(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        CompanyPaymentMethod.CREDIT_CARD,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        CompanyModulePaymentStatus.DRAFT_PANIER,
        0,
        0,
        0,
        currentDate,
        currentDate,
        false,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            paymentDate: currentDate.format(DATE_FORMAT),
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CompanyBillPayment', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            paymentDate: currentDate.format(DATE_FORMAT),
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
            date: currentDate,
            dueDate: currentDate,
          },
          returnedFromService
        );

        service.create(new CompanyBillPayment()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CompanyBillPayment', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            details: 'BBBBBB',
            paymentDate: currentDate.format(DATE_FORMAT),
            paymentMethod: 'BBBBBB',
            bankCheckorTraitNumber: 'BBBBBB',
            imageJustif: 'BBBBBB',
            status: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
            withTVA: true,
            withTax: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
            date: currentDate,
            dueDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CompanyBillPayment', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            details: 'BBBBBB',
            paymentDate: currentDate.format(DATE_FORMAT),
            paymentMethod: 'BBBBBB',
            bankCheckorTraitNumber: 'BBBBBB',
            imageJustif: 'BBBBBB',
            status: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
            withTVA: true,
            withTax: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
            date: currentDate,
            dueDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CompanyBillPayment', () => {
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
