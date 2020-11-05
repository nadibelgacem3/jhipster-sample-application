import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CompanyBankAccountService } from 'app/entities/mooincompanies/company-bank-account/company-bank-account.service';
import { ICompanyBankAccount, CompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';

describe('Service Tests', () => {
  describe('CompanyBankAccount Service', () => {
    let injector: TestBed;
    let service: CompanyBankAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompanyBankAccount;
    let expectedResult: ICompanyBankAccount | ICompanyBankAccount[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CompanyBankAccountService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CompanyBankAccount(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CompanyBankAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CompanyBankAccount()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CompanyBankAccount', () => {
        const returnedFromService = Object.assign(
          {
            reference: 'BBBBBB',
            bankName: 'BBBBBB',
            bankAccountNumber: 'BBBBBB',
            iban: 'BBBBBB',
            swift: 'BBBBBB',
            type: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CompanyBankAccount', () => {
        const returnedFromService = Object.assign(
          {
            reference: 'BBBBBB',
            bankName: 'BBBBBB',
            bankAccountNumber: 'BBBBBB',
            iban: 'BBBBBB',
            swift: 'BBBBBB',
            type: 'BBBBBB',
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

      it('should delete a CompanyBankAccount', () => {
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
