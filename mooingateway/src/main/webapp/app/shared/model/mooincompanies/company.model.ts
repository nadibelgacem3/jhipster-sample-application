import { ICompanyBankAccount } from 'app/shared/model/mooincompanies/company-bank-account.model';
import { ICompanyLocation } from 'app/shared/model/mooincompanies/company-location.model';
import { CompanyBusinessTypeEnum } from 'app/shared/model/enumerations/company-business-type-enum.model';

export interface ICompany {
  id?: number;
  name?: string;
  logoContentType?: string;
  logo?: any;
  phone1?: string;
  phone2?: string;
  fax?: string;
  email1?: string;
  email2?: string;
  description?: string;
  businessType?: CompanyBusinessTypeEnum;
  currencyUnit?: string;
  currencyQuotient?: number;
  commercialRegister?: string;
  isActivated?: boolean;
  themeColor?: string;
  facebook?: string;
  linkedin?: string;
  twitter?: string;
  instagram?: string;
  companyBankAccount?: ICompanyBankAccount;
  companyLocation?: ICompanyLocation;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public logoContentType?: string,
    public logo?: any,
    public phone1?: string,
    public phone2?: string,
    public fax?: string,
    public email1?: string,
    public email2?: string,
    public description?: string,
    public businessType?: CompanyBusinessTypeEnum,
    public currencyUnit?: string,
    public currencyQuotient?: number,
    public commercialRegister?: string,
    public isActivated?: boolean,
    public themeColor?: string,
    public facebook?: string,
    public linkedin?: string,
    public twitter?: string,
    public instagram?: string,
    public companyBankAccount?: ICompanyBankAccount,
    public companyLocation?: ICompanyLocation
  ) {
    this.isActivated = this.isActivated || false;
  }
}
