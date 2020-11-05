import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MooingatewayTestModule } from '../../../../test.module';
import { TransactionCompDetailComponent } from 'app/entities/mooincompanies/transaction-comp/transaction-comp-detail.component';
import { TransactionComp } from 'app/shared/model/mooincompanies/transaction-comp.model';

describe('Component Tests', () => {
  describe('TransactionComp Management Detail Component', () => {
    let comp: TransactionCompDetailComponent;
    let fixture: ComponentFixture<TransactionCompDetailComponent>;
    const route = ({ data: of({ transactionComp: new TransactionComp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MooingatewayTestModule],
        declarations: [TransactionCompDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TransactionCompDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionCompDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load transactionComp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionComp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
