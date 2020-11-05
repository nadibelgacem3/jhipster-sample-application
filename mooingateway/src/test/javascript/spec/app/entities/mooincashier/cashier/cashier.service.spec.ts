import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CashierService } from 'app/entities/mooincashier/cashier/cashier.service';
import { ICashier, Cashier } from 'app/shared/model/mooincashier/cashier.model';

describe('Service Tests', () => {
  describe('Cashier Service', () => {
    let injector: TestBed;
    let service: CashierService;
    let httpMock: HttpTestingController;
    let elemDefault: ICashier;
    let expectedResult: ICashier | ICashier[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CashierService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Cashier(0, 'AAAAAAA', false, false, false, false, 'AAAAAAA', false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Cashier', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Cashier()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Cashier', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            withTicket: true,
            withTVA: true,
            withTax: true,
            withAppro: true,
            themeColor: 'BBBBBB',
            isActivated: true,
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

      it('should return a list of Cashier', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            withTicket: true,
            withTVA: true,
            withTax: true,
            withAppro: true,
            themeColor: 'BBBBBB',
            isActivated: true,
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

      it('should delete a Cashier', () => {
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
