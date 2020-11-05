import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyBankAccountComponent } from 'app/entities/mooincompanies/company-bank-account/company-bank-account.component';
import { CompanyBankAccountService } from 'app/entities/mooincompanies/company-bank-account/company-bank-account.service';
import { CompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';

describe('Component Tests', () => {
  describe('CompanyBankAccount Management Component', () => {
    let comp: CompanyBankAccountComponent;
    let fixture: ComponentFixture<CompanyBankAccountComponent>;
    let service: CompanyBankAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyBankAccountComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(CompanyBankAccountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyBankAccountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyBankAccountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompanyBankAccount(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.companyBankAccounts && comp.companyBankAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompanyBankAccount(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.companyBankAccounts && comp.companyBankAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
