import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CompanyBillPaymentItemService } from 'app/entities/mooincompanies/company-bill-payment-item/company-bill-payment-item.service';
import { ICompanyBillPaymentItem, CompanyBillPaymentItem } from 'app/shared/model/mooincompanies/company-bill-payment-item.model';

describe('Service Tests', () => {
  describe('CompanyBillPaymentItem Service', () => {
    let injector: TestBed;
    let service: CompanyBillPaymentItemService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompanyBillPaymentItem;
    let expectedResult: ICompanyBillPaymentItem | ICompanyBillPaymentItem[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CompanyBillPaymentItemService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CompanyBillPaymentItem(0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CompanyBillPaymentItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CompanyBillPaymentItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CompanyBillPaymentItem', () => {
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

      it('should return a list of CompanyBillPaymentItem', () => {
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

      it('should delete a CompanyBillPaymentItem', () => {
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
