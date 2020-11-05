import { ICashier } from 'app/shared/model/mooincashier/cashier.model';
import { ICashierCostumer } from 'app/shared/model/mooincashier/cashier-costumer.model';

export interface ICashierLocation {
  id?: number;
  localNumber?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  countryName?: string;
  flagContentType?: string;
  flag?: any;
  cashier?: ICashier;
  cashierCostumer?: ICashierCostumer;
}

export class CashierLocation implements ICashierLocation {
  constructor(
    public id?: number,
    public localNumber?: string,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public stateProvince?: string,
    public countryName?: string,
    public flagContentType?: string,
    public flag?: any,
    public cashier?: ICashier,
    public cashierCostumer?: ICashierCostumer
  ) {}
}
