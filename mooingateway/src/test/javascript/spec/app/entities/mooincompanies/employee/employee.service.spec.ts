import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeeService } from 'app/entities/mooincompanies/employee/employee.service';
import { IEmployee, Employee } from 'app/shared/model/mooincompanies/employee.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

describe('Service Tests', () => {
  describe('Employee Service', () => {
    let injector: TestBed;
    let service: EmployeeService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployee;
    let expectedResult: IEmployee | IEmployee[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmployeeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Employee(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Gender.MALE,
        currentDate,
        currentDate,
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfborth: currentDate.format(DATE_FORMAT),
            dateOfRecruitment: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Employee', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfborth: currentDate.format(DATE_FORMAT),
            dateOfRecruitment: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfborth: currentDate,
            dateOfRecruitment: currentDate,
          },
          returnedFromService
        );

        service.create(new Employee()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Employee', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            jobTitle: 'BBBBBB',
            gender: 'BBBBBB',
            dateOfborth: currentDate.format(DATE_FORMAT),
            dateOfRecruitment: currentDate.format(DATE_FORMAT),
            image: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            salary: 1,
            commissionPct: 1,
            rates: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfborth: currentDate,
            dateOfRecruitment: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Employee', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            jobTitle: 'BBBBBB',
            gender: 'BBBBBB',
            dateOfborth: currentDate.format(DATE_FORMAT),
            dateOfRecruitment: currentDate.format(DATE_FORMAT),
            image: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            salary: 1,
            commissionPct: 1,
            rates: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfborth: currentDate,
            dateOfRecruitment: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Employee', () => {
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
