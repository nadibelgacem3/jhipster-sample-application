import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AvoirService } from 'app/entities/mooinbase/avoir/avoir.service';
import { IAvoir, Avoir } from 'app/shared/model/mooinbase/avoir.model';
import { AvoirStatusEnum } from 'app/shared/model/enumerations/avoir-status-enum.model';
import { AvoirTypeEnum } from 'app/shared/model/enumerations/avoir-type-enum.model';

describe('Service Tests', () => {
  describe('Avoir Service', () => {
    let injector: TestBed;
    let service: AvoirService;
    let httpMock: HttpTestingController;
    let elemDefault: IAvoir;
    let expectedResult: IAvoir | IAvoir[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AvoirService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Avoir(
        0,
        'AAAAAAA',
        'AAAAAAA',
        AvoirStatusEnum.DRAFT,
        AvoirTypeEnum.SALE,
        0,
        0,
        0,
        currentDate,
        currentDate,
        false,
        0
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

      it('should create a Avoir', () => {
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

        service.create(new Avoir()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Avoir', () => {
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

      it('should return a list of Avoir', () => {
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

      it('should delete a Avoir', () => {
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
