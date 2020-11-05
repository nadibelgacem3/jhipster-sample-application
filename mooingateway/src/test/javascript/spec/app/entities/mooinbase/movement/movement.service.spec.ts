import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MovementService } from 'app/entities/mooinbase/movement/movement.service';
import { IMovement, Movement } from 'app/shared/model/mooinbase/movement.model';
import { MovementTypeEnum } from 'app/shared/model/enumerations/movement-type-enum.model';
import { MovementReasonEnum } from 'app/shared/model/enumerations/movement-reason-enum.model';

describe('Service Tests', () => {
  describe('Movement Service', () => {
    let injector: TestBed;
    let service: MovementService;
    let httpMock: HttpTestingController;
    let elemDefault: IMovement;
    let expectedResult: IMovement | IMovement[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MovementService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Movement(0, MovementTypeEnum.INPUT, MovementReasonEnum.INVOICE, currentDate, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Movement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.create(new Movement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Movement', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            reason: 'BBBBBB',
            date: currentDate.format(DATE_TIME_FORMAT),
            billID: 1,
            tiersID: 1,
            quantity: 1,
            companyUserID: 1,
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

      it('should return a list of Movement', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            reason: 'BBBBBB',
            date: currentDate.format(DATE_TIME_FORMAT),
            billID: 1,
            tiersID: 1,
            quantity: 1,
            companyUserID: 1,
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

      it('should delete a Movement', () => {
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
