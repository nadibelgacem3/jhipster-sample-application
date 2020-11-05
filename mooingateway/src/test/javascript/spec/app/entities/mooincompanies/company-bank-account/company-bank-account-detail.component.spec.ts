import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { CompanyBankAccountDetailComponent } from 'app/entities/mooincompanies/company-bank-account/company-bank-account-detail.component';
import { CompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';

describe('Component Tests', () => {
  describe('CompanyBankAccount Management Detail Component', () => {
    let comp: CompanyBankAccountDetailComponent;
    let fixture: ComponentFixture<CompanyBankAccountDetailComponent>;
    const route = ({ data: of({ companyBankAccount: new CompanyBankAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [CompanyBankAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CompanyBankAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyBankAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load companyBankAccount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companyBankAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
