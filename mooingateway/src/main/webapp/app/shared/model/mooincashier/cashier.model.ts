import { ICashierLocation } from 'app/shared/model/mooincashier/cashier-location.model';

export interface ICashier {
  id?: number;
  name?: string;
  withTicket?: boolean;
  withTVA?: boolean;
  withTax?: boolean;
  withAppro?: boolean;
  themeColor?: string;
  isActivated?: boolean;
  companyID?: number;
  cashierLocation?: ICashierLocation;
}

export class Cashier implements ICashier {
  constructor(
    public id?: number,
    public name?: string,
    public withTicket?: boolean,
    public withTVA?: boolean,
    public withTax?: boolean,
    public withAppro?: boolean,
    public themeColor?: string,
    public isActivated?: boolean,
    public companyID?: number,
    public cashierLocation?: ICashierLocation
  ) {
    this.withTicket = this.withTicket || false;
    this.withTVA = this.withTVA || false;
    this.withTax = this.withTax || false;
    this.withAppro = this.withAppro || false;
    this.isActivated = this.isActivated || false;
  }
}
