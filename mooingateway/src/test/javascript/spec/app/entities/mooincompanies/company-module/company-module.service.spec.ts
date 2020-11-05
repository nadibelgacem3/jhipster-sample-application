import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CompanyModuleService } from 'app/entities/mooincompanies/company-module/company-module.service';
import { ICompanyModule, CompanyModule } from 'app/shared/model/mooincompanies/company-module.model';

describe('Service Tests', () => {
  describe('CompanyModule Service', () => {
    let injector: TestBed;
    let service: CompanyModuleService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompanyModule;
    let expectedResult: ICompanyModule | ICompanyModule[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CompanyModuleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CompanyModule(0, 'AAAAAAA', currentDate, currentDate, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            activatedAt: currentDate.format(DATE_FORMAT),
            activatedUntil: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CompanyModule', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            activatedAt: currentDate.format(DATE_FORMAT),
            activatedUntil: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            activatedAt: currentDate,
            activatedUntil: currentDate,
          },
          returnedFromService
        );

        service.create(new CompanyModule()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CompanyModule', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            activatedAt: currentDate.format(DATE_FORMAT),
            activatedUntil: currentDate.format(DATE_FORMAT),
            isActivated: true,
            price: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            activatedAt: currentDate,
            activatedUntil: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CompanyModule', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            activatedAt: currentDate.format(DATE_FORMAT),
            activatedUntil: currentDate.format(DATE_FORMAT),
            isActivated: true,
            price: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            activatedAt: currentDate,
            activatedUntil: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CompanyModule', () => {
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
