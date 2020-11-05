import { ICompany } from 'app/shared/model/mooincompanies/company.model';

export interface ICompanyBankAccount {
  id?: number;
  reference?: string;
  bankName?: string;
  bankAccountNumber?: string;
  iban?: string;
  swift?: string;
  type?: string;
  company?: ICompany;
}

export class CompanyBankAccount implements ICompanyBankAccount {
  constructor(
    public id?: number,
    public reference?: string,
    public bankName?: string,
    public bankAccountNumber?: string,
    public iban?: string,
    public swift?: string,
    public type?: string,
    public company?: ICompany
  ) {}
}
