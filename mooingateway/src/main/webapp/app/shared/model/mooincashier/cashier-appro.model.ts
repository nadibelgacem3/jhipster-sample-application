import { Moment } from 'moment';
import { ICashierApproItem } from 'app/shared/model/mooincashier/cashier-appro-item.model';
import { ICashier } from 'app/shared/model/mooincashier/cashier.model';
import { CashierApproStatusEnum } from 'app/shared/model/enumerations/cashier-appro-status-enum.model';

export interface ICashierAppro {
  id?: number;
  number?: string;
  status?: CashierApproStatusEnum;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  date?: Moment;
  isConverted?: boolean;
  withTVA?: boolean;
  withTax?: boolean;
  cashierApproItems?: ICashierApproItem[];
  cashier?: ICashier;
}

export class CashierAppro implements ICashierAppro {
  constructor(
    public id?: number,
    public number?: string,
    public status?: CashierApproStatusEnum,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public date?: Moment,
    public isConverted?: boolean,
    public withTVA?: boolean,
    public withTax?: boolean,
    public cashierApproItems?: ICashierApproItem[],
    public cashier?: ICashier
  ) {
    this.isConverted = this.isConverted || false;
    this.withTVA = this.withTVA || false;
    this.withTax = this.withTax || false;
  }
}
