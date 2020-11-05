import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BLService } from 'app/entities/mooinbase/bl/bl.service';
import { IBL, BL } from 'app/shared/model/mooinbase/bl.model';
import { BLStatusEnum } from 'app/shared/model/enumerations/bl-status-enum.model';
import { BLTypeEnum } from 'app/shared/model/enumerations/bl-type-enum.model';

describe('Service Tests', () => {
  describe('BL Service', () => {
    let injector: TestBed;
    let service: BLService;
    let httpMock: HttpTestingController;
    let elemDefault: IBL;
    let expectedResult: IBL | IBL[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BLService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BL(0, 'AAAAAAA', 'AAAAAAA', BLStatusEnum.DRAFT, BLTypeEnum.SALE, 0, 0, 0, currentDate, currentDate, false, 0);
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

      it('should create a BL', () => {
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

        service.create(new BL()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BL', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            reference: 'BBBBBB',
            status: 'BBBBBB',
            type: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
            isConverted: true,
            companyID: 1,
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

      it('should return a list of BL', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            reference: 'BBBBBB',
            status: 'BBBBBB',
            type: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
            isConverted: true,
            companyID: 1,
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

      it('should delete a BL', () => {
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
