import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CashierProductService } from 'app/entities/mooincashier/cashier-product/cashier-product.service';
import { ICashierProduct, CashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';

describe('Service Tests', () => {
  describe('CashierProduct Service', () => {
    let injector: TestBed;
    let service: CashierProductService;
    let httpMock: HttpTestingController;
    let elemDefault: ICashierProduct;
    let expectedResult: ICashierProduct | ICashierProduct[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CashierProductService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CashierProduct(0, 0, 'AAAAAAA', 0, 0, 0, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CashierProduct', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CashierProduct()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CashierProduct', () => {
        const returnedFromService = Object.assign(
          {
            productID: 1,
            cashierProdName: 'BBBBBB',
            cashierProdQty: 1,
            cashierProdPurchaseUnitPrice: 1,
            cashierProdSaleUnitPrice: 1,
            cashierProdStockLimit: 1,
            cashierProdStockLimitAlert: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CashierProduct', () => {
        const returnedFromService = Object.assign(
          {
            productID: 1,
            cashierProdName: 'BBBBBB',
            cashierProdQty: 1,
            cashierProdPurchaseUnitPrice: 1,
            cashierProdSaleUnitPrice: 1,
            cashierProdStockLimit: 1,
            cashierProdStockLimitAlert: true,
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

      it('should delete a CashierProduct', () => {
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
