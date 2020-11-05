import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CashierCostumerService } from 'app/entities/mooincashier/cashier-costumer/cashier-costumer.service';
import { ICashierCostumer, CashierCostumer } from 'app/shared/model/mooincashier/cashier-costumer.model';

describe('Service Tests', () => {
  describe('CashierCostumer Service', () => {
    let injector: TestBed;
    let service: CashierCostumerService;
    let httpMock: HttpTestingController;
    let elemDefault: ICashierCostumer;
    let expectedResult: ICashierCostumer | ICashierCostumer[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CashierCostumerService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CashierCostumer(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CashierCostumer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CashierCostumer()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CashierCostumer', () => {
        const returnedFromService = Object.assign(
          {
            reference: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            phone1: 'BBBBBB',
            phone2: 'BBBBBB',
            image: 'BBBBBB',
            email: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CashierCostumer', () => {
        const returnedFromService = Object.assign(
          {
            reference: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            phone1: 'BBBBBB',
            phone2: 'BBBBBB',
            image: 'BBBBBB',
            email: 'BBBBBB',
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

      it('should delete a CashierCostumer', () => {
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
