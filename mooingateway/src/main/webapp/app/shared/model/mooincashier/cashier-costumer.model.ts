import { ICashierLocation } from 'app/shared/model/mooincashier/cashier-location.model';
import { ICashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';
import { ICashier } from 'app/shared/model/mooincashier/cashier.model';

export interface ICashierCostumer {
  id?: number;
  reference?: string;
  firstName?: string;
  lastName?: string;
  phone1?: string;
  phone2?: string;
  imageContentType?: string;
  image?: any;
  email?: string;
  cashierLocation?: ICashierLocation;
  cashierReceipts?: ICashierReceipt[];
  cashier?: ICashier;
}

export class CashierCostumer implements ICashierCostumer {
  constructor(
    public id?: number,
    public reference?: string,
    public firstName?: string,
    public lastName?: string,
    public phone1?: string,
    public phone2?: string,
    public imageContentType?: string,
    public image?: any,
    public email?: string,
    public cashierLocation?: ICashierLocation,
    public cashierReceipts?: ICashierReceipt[],
    public cashier?: ICashier
  ) {}
}
