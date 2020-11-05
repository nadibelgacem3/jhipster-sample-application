import { Moment } from 'moment';
import { ICashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';
import { CashierReceiptPayMeth } from 'app/shared/model/enumerations/cashier-receipt-pay-meth.model';

export interface ICashierReceiptPay {
  id?: number;
  details?: string;
  amount?: number;
  paymentDate?: Moment;
  paymentMethod?: CashierReceiptPayMeth;
  bankCheckorTraitNumber?: string;
  imageJustifContentType?: string;
  imageJustif?: any;
  cashierReceipt?: ICashierReceipt;
}

export class CashierReceiptPay implements ICashierReceiptPay {
  constructor(
    public id?: number,
    public details?: string,
    public amount?: number,
    public paymentDate?: Moment,
    public paymentMethod?: CashierReceiptPayMeth,
    public bankCheckorTraitNumber?: string,
    public imageJustifContentType?: string,
    public imageJustif?: any,
    public cashierReceipt?: ICashierReceipt
  ) {}
}
