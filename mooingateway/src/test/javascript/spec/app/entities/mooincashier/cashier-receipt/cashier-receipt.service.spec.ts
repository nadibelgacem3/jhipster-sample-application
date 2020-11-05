import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CashierReceiptService } from 'app/entities/mooincashier/cashier-receipt/cashier-receipt.service';
import { ICashierReceipt, CashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';
import { CashierReceiptStatusEnum } from 'app/shared/model/enumerations/cashier-receipt-status-enum.model';

describe('Service Tests', () => {
  describe('CashierReceipt Service', () => {
    let injector: TestBed;
    let service: CashierReceiptService;
    let httpMock: HttpTestingController;
    let elemDefault: ICashierReceipt;
    let expectedResult: ICashierReceipt | ICashierReceipt[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CashierReceiptService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CashierReceipt(
        0,
        'AAAAAAA',
        'AAAAAAA',
        CashierReceiptStatusEnum.DRAFT,
        0,
        0,
        0,
        currentDate,
        currentDate,
        false,
        false,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
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

      it('should create a CashierReceipt', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            dueDate: currentDate,
          },
          returnedFromService
        );

        service.create(new CashierReceipt()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CashierReceipt', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            reference: 'BBBBBB',
            status: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
            isConverted: true,
            withTVA: true,
            withTax: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should return a list of CashierReceipt', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            reference: 'BBBBBB',
            status: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
            isConverted: true,
            withTVA: true,
            withTax: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a CashierReceipt', () => {
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
