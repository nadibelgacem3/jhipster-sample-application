import { ICashierProduct } from 'app/shared/model/mooincashier/cashier-product.model';
import { ICashierAppro } from 'app/shared/model/mooincashier/cashier-appro.model';

export interface ICashierApproItem {
  id?: number;
  quantity?: number;
  discountRate?: number;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  cashierProduct?: ICashierProduct;
  cashierAppro?: ICashierAppro;
}

export class CashierApproItem implements ICashierApproItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public discountRate?: number,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public cashierProduct?: ICashierProduct,
    public cashierAppro?: ICashierAppro
  ) {}
}
