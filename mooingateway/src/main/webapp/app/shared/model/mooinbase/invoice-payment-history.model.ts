import { Moment } from 'moment';
import { IInvoice } from 'app/shared/model/mooinbase/invoice.model';
import { InvoicePaymentMethod } from 'app/shared/model/enumerations/invoice-payment-method.model';

export interface IInvoicePaymentHistory {
  id?: number;
  details?: string;
  amount?: number;
  paymentDate?: Moment;
  paymentMethod?: InvoicePaymentMethod;
  bankCheckorTraitNumber?: string;
  imageJustifContentType?: string;
  imageJustif?: any;
  invoice?: IInvoice;
}

export class InvoicePaymentHistory implements IInvoicePaymentHistory {
  constructor(
    public id?: number,
    public details?: string,
    public amount?: number,
    public paymentDate?: Moment,
    public paymentMethod?: InvoicePaymentMethod,
    public bankCheckorTraitNumber?: string,
    public imageJustifContentType?: string,
    public imageJustif?: any,
    public invoice?: IInvoice
  ) {}
}
