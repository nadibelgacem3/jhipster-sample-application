import { Moment } from 'moment';
import { IInvoiceItem } from 'app/shared/model/mooinbase/invoice-item.model';
import { IInvoicePaymentHistory } from 'app/shared/model/mooinbase/invoice-payment-history.model';
import { ITiers } from 'app/shared/model/mooinbase/tiers.model';
import { InvoiceStatusEnum } from 'app/shared/model/enumerations/invoice-status-enum.model';
import { InvoiceTypeEnum } from 'app/shared/model/enumerations/invoice-type-enum.model';

export interface IInvoice {
  id?: number;
  number?: string;
  reference?: string;
  status?: InvoiceStatusEnum;
  type?: InvoiceTypeEnum;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  date?: Moment;
  dueDate?: Moment;
  companyID?: number;
  invoiceItems?: IInvoiceItem[];
  invoicePaymentHistories?: IInvoicePaymentHistory[];
  tiers?: ITiers;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public number?: string,
    public reference?: string,
    public status?: InvoiceStatusEnum,
    public type?: InvoiceTypeEnum,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public date?: Moment,
    public dueDate?: Moment,
    public companyID?: number,
    public invoiceItems?: IInvoiceItem[],
    public invoicePaymentHistories?: IInvoicePaymentHistory[],
    public tiers?: ITiers
  ) {}
}
