import { Moment } from 'moment';
import { ICompanyBillPaymentItem } from 'app/shared/model/mooincompanies/company-bill-payment-item.model';
import { ICompany } from 'app/shared/model/mooincompanies/company.model';
import { CompanyPaymentMethod } from 'app/shared/model/enumerations/company-payment-method.model';
import { CompanyModulePaymentStatus } from 'app/shared/model/enumerations/company-module-payment-status.model';

export interface ICompanyBillPayment {
  id?: number;
  number?: string;
  details?: string;
  paymentDate?: Moment;
  paymentMethod?: CompanyPaymentMethod;
  bankCheckorTraitNumber?: string;
  imageJustifContentType?: string;
  imageJustif?: any;
  status?: CompanyModulePaymentStatus;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  date?: Moment;
  dueDate?: Moment;
  withTVA?: boolean;
  withTax?: boolean;
  companyBillPaymentItems?: ICompanyBillPaymentItem[];
  company?: ICompany;
}

export class CompanyBillPayment implements ICompanyBillPayment {
  constructor(
    public id?: number,
    public number?: string,
    public details?: string,
    public paymentDate?: Moment,
    public paymentMethod?: CompanyPaymentMethod,
    public bankCheckorTraitNumber?: string,
    public imageJustifContentType?: string,
    public imageJustif?: any,
    public status?: CompanyModulePaymentStatus,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public date?: Moment,
    public dueDate?: Moment,
    public withTVA?: boolean,
    public withTax?: boolean,
    public companyBillPaymentItems?: ICompanyBillPaymentItem[],
    public company?: ICompany
  ) {
    this.withTVA = this.withTVA || false;
    this.withTax = this.withTax || false;
  }
}
