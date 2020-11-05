import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TiersBankCheckService } from 'app/entities/mooinbase/tiers-bank-check/tiers-bank-check.service';
import { ITiersBankCheck, TiersBankCheck } from 'app/shared/model/mooinbase/tiers-bank-check.model';
import { BankCheckType } from 'app/shared/model/enumerations/bank-check-type.model';
import { BankCheckKind } from 'app/shared/model/enumerations/bank-check-kind.model';

describe('Service Tests', () => {
  describe('TiersBankCheck Service', () => {
    let injector: TestBed;
    let service: TiersBankCheckService;
    let httpMock: HttpTestingController;
    let elemDefault: ITiersBankCheck;
    let expectedResult: ITiersBankCheck | ITiersBankCheck[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TiersBankCheckService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TiersBankCheck(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate,
        BankCheckType.CHECK,
        BankCheckKind.RECEIVED,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dueDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TiersBankCheck', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dueDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dueDate: currentDate,
          },
          returnedFromService
        );

        service.create(new TiersBankCheck()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TiersBankCheck', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            bankName: 'BBBBBB',
            number: 'BBBBBB',
            amount: 1,
            dueDate: currentDate.format(DATE_FORMAT),
            bankCheckType: 'BBBBBB',
            bankCheckKind: 'BBBBBB',
            swift: 'BBBBBB',
            type: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dueDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TiersBankCheck', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            bankName: 'BBBBBB',
            number: 'BBBBBB',
            amount: 1,
            dueDate: currentDate.format(DATE_FORMAT),
            bankCheckType: 'BBBBBB',
            bankCheckKind: 'BBBBBB',
            swift: 'BBBBBB',
            type: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a TiersBankCheck', () => {
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
