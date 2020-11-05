import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TiersService } from 'app/entities/mooinbase/tiers/tiers.service';
import { ITiers, Tiers } from 'app/shared/model/mooinbase/tiers.model';
import { TiersTypeEnum } from 'app/shared/model/enumerations/tiers-type-enum.model';

describe('Service Tests', () => {
  describe('Tiers Service', () => {
    let injector: TestBed;
    let service: TiersService;
    let httpMock: HttpTestingController;
    let elemDefault: ITiers;
    let expectedResult: ITiers | ITiers[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TiersService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Tiers(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        TiersTypeEnum.PERSON,
        false,
        false,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Tiers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Tiers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Tiers', () => {
        const returnedFromService = Object.assign(
          {
            reference: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            phone1: 'BBBBBB',
            phone2: 'BBBBBB',
            image: 'BBBBBB',
            email: 'BBBBBB',
            type: 'BBBBBB',
            isCustomer: true,
            isSupplier: true,
            companyID: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Tiers', () => {
        const returnedFromService = Object.assign(
          {
            reference: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            phone1: 'BBBBBB',
            phone2: 'BBBBBB',
            image: 'BBBBBB',
            email: 'BBBBBB',
            type: 'BBBBBB',
            isCustomer: true,
            isSupplier: true,
            companyID: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Tiers', () => {
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
