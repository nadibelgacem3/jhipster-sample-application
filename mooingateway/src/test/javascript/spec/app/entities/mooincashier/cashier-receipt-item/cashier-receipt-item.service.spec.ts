import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CashierReceiptItemService } from 'app/entities/mooincashier/cashier-receipt-item/cashier-receipt-item.service';
import { ICashierReceiptItem, CashierReceiptItem } from 'app/shared/model/mooincashier/cashier-receipt-item.model';

describe('Service Tests', () => {
  describe('CashierReceiptItem Service', () => {
    let injector: TestBed;
    let service: CashierReceiptItemService;
    let httpMock: HttpTestingController;
    let elemDefault: ICashierReceiptItem;
    let expectedResult: ICashierReceiptItem | ICashierReceiptItem[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CashierReceiptItemService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CashierReceiptItem(0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CashierReceiptItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CashierReceiptItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CashierReceiptItem', () => {
        const returnedFromService = Object.assign(
          {
            quantity: 1,
            discountRate: 1,
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CashierReceiptItem', () => {
        const returnedFromService = Object.assign(
          {
            quantity: 1,
            discountRate: 1,
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
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

      it('should delete a CashierReceiptItem', () => {
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
