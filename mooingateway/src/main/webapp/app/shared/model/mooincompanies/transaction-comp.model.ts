import { Moment } from 'moment';
import { ICompany } from 'app/shared/model/mooincompanies/company.model';
import { TransactionCompTypeEnum } from 'app/shared/model/enumerations/transaction-comp-type-enum.model';
import { TransactionCompPaymentMethod } from 'app/shared/model/enumerations/transaction-comp-payment-method.model';

export interface ITransactionComp {
  id?: number;
  number?: string;
  details?: string;
  type?: TransactionCompTypeEnum;
  paymentMethod?: TransactionCompPaymentMethod;
  withTVA?: boolean;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  date?: Moment;
  company?: ICompany;
}

export class TransactionComp implements ITransactionComp {
  constructor(
    public id?: number,
    public number?: string,
    public details?: string,
    public type?: TransactionCompTypeEnum,
    public paymentMethod?: TransactionCompPaymentMethod,
    public withTVA?: boolean,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public date?: Moment,
    public company?: ICompany
  ) {
    this.withTVA = this.withTVA || false;
  }
}
