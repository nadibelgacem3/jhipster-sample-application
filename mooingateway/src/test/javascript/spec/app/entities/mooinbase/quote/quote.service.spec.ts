import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { QuoteService } from 'app/entities/mooinbase/quote/quote.service';
import { IQuote, Quote } from 'app/shared/model/mooinbase/quote.model';
import { QuoteStatusEnum } from 'app/shared/model/enumerations/quote-status-enum.model';
import { QuoteTypeEnum } from 'app/shared/model/enumerations/quote-type-enum.model';

describe('Service Tests', () => {
  describe('Quote Service', () => {
    let injector: TestBed;
    let service: QuoteService;
    let httpMock: HttpTestingController;
    let elemDefault: IQuote;
    let expectedResult: IQuote | IQuote[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(QuoteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Quote(
        0,
        'AAAAAAA',
        'AAAAAAA',
        QuoteStatusEnum.DRAFT,
        QuoteTypeEnum.SALE,
        0,
        0,
        0,
        currentDate,
        currentDate,
        false,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Quote', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            dueDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Quote()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Quote', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            reference: 'BBBBBB',
            status: 'BBBBBB',
            type: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
            isConverted: true,
            companyID: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            dueDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Quote', () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            reference: 'BBBBBB',
            status: 'BBBBBB',
            type: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totaTTC: 1,
            date: currentDate.format(DATE_FORMAT),
            dueDate: currentDate.format(DATE_FORMAT),
            isConverted: true,
            companyID: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            dueDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Quote', () => {
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
