import { ICompanyBillPayment } from 'app/shared/model/mooincompanies/company-bill-payment.model';
import { ICompanyModule } from 'app/shared/model/mooincompanies/company-module.model';

export interface ICompanyBillPaymentItem {
  id?: number;
  quantity?: number;
  discountRate?: number;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  companyBillPayment?: ICompanyBillPayment;
  companyModule?: ICompanyModule;
}

export class CompanyBillPaymentItem implements ICompanyBillPaymentItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public discountRate?: number,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public companyBillPayment?: ICompanyBillPayment,
    public companyModule?: ICompanyModule
  ) {}
}
