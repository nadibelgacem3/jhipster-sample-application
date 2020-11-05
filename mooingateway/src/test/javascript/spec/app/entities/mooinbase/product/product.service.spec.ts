import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProductService } from 'app/entities/mooinbase/product/product.service';
import { IProduct, Product } from 'app/shared/model/mooinbase/product.model';
import { ProductUnitEnum } from 'app/shared/model/enumerations/product-unit-enum.model';
import { Size } from 'app/shared/model/enumerations/size.model';

describe('Service Tests', () => {
  describe('Product Service', () => {
    let injector: TestBed;
    let service: ProductService;
    let httpMock: HttpTestingController;
    let elemDefault: IProduct;
    let expectedResult: IProduct | IProduct[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProductService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Product(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        false,
        ProductUnitEnum.NONE,
        Size.NONE,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        false
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

      it('should create a Product', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Product()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Product', () => {
        const returnedFromService = Object.assign(
          {
            companyID: 1,
            reference: 'BBBBBB',
            referenceProvider: 'BBBBBB',
            name: 'BBBBBB',
            quantity: 1,
            purchaseUnitPrice: 1,
            saleUnitPrice: 1,
            stocklimit: 1,
            stocklimitAlert: true,
            unitType: 'BBBBBB',
            size: 'BBBBBB',
            color: 'BBBBBB',
            image: 'BBBBBB',
            isDisplayedInCashier: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Product', () => {
        const returnedFromService = Object.assign(
          {
            companyID: 1,
            reference: 'BBBBBB',
            referenceProvider: 'BBBBBB',
            name: 'BBBBBB',
            quantity: 1,
            purchaseUnitPrice: 1,
            saleUnitPrice: 1,
            stocklimit: 1,
            stocklimitAlert: true,
            unitType: 'BBBBBB',
            size: 'BBBBBB',
            color: 'BBBBBB',
            image: 'BBBBBB',
            isDisplayedInCashier: true,
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

      it('should delete a Product', () => {
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
