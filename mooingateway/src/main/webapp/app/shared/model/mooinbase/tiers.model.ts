import { ITiersLocation } from 'app/shared/model/mooinbase/tiers-location.model';
import { TiersTypeEnum } from 'app/shared/model/enumerations/tiers-type-enum.model';

export interface ITiers {
  id?: number;
  reference?: string;
  firstName?: string;
  lastName?: string;
  phone1?: string;
  phone2?: string;
  imageContentType?: string;
  image?: any;
  email?: string;
  type?: TiersTypeEnum;
  isCustomer?: boolean;
  isSupplier?: boolean;
  companyID?: number;
  tiersLocation?: ITiersLocation;
}

export class Tiers implements ITiers {
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
    public type?: TiersTypeEnum,
    public isCustomer?: boolean,
    public isSupplier?: boolean,
    public companyID?: number,
    public tiersLocation?: ITiersLocation
  ) {
    this.isCustomer = this.isCustomer || false;
    this.isSupplier = this.isSupplier || false;
  }
}
