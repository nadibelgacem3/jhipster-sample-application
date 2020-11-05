import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CashierApproService } from 'app/entities/mooincashier/cashier-appro/cashier-appro.service';
import { ICashierAppro, CashierAppro } from 'app/shared/model/mooincashier/cashier-appro.model';
import { CashierApproStatusEnum } from 'app/shared/model/enumerations/cashier-appro-status-enum.model';

describe('Service Tests', () => {
  describe('CashierAppro Service', () => {
    let injector: TestBed;
    let service: CashierApproService;
    let httpMock: HttpTestingController;
    let elemDefault: ICashierAppro;
    let expectedResult: ICashierAppro | ICashierAppro[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CashierApproService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CashierAppro(0, 'AAAAAAA', CashierApproStatusEnum.DRAFT, 0, 0, 0, currentDate, false, false, false);
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

      it('should create a CashierAppro', () => {
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

        service.create(new CashierAppro()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CashierAppro', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            status: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            isConverted: true,
            withTVA: true,
            withTax: true,
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

      it('should return a list of CashierAppro', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            status: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            isConverted: true,
            withTVA: true,
            withTax: true,
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

      it('should delete a CashierAppro', () => {
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
