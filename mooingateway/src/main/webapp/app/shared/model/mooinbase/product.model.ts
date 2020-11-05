import { IMovement } from 'app/shared/model/mooinbase/movement.model';
import { ITaxItem } from 'app/shared/model/mooinbase/tax-item.model';
import { ITVAItem } from 'app/shared/model/mooinbase/tva-item.model';
import { IProductCategory } from 'app/shared/model/mooinbase/product-category.model';
import { IProductMark } from 'app/shared/model/mooinbase/product-mark.model';
import { IDepot } from 'app/shared/model/mooinbase/depot.model';
import { ProductUnitEnum } from 'app/shared/model/enumerations/product-unit-enum.model';
import { Size } from 'app/shared/model/enumerations/size.model';

export interface IProduct {
  id?: number;
  companyID?: number;
  reference?: string;
  referenceProvider?: string;
  name?: string;
  quantity?: number;
  purchaseUnitPrice?: number;
  saleUnitPrice?: number;
  stocklimit?: number;
  stocklimitAlert?: boolean;
  unitType?: ProductUnitEnum;
  size?: Size;
  color?: string;
  imageContentType?: string;
  image?: any;
  isDisplayedInCashier?: boolean;
  movements?: IMovement[];
  taxItems?: ITaxItem[];
  tvaItems?: ITVAItem[];
  productCategory?: IProductCategory;
  productMark?: IProductMark;
  depot?: IDepot;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public companyID?: number,
    public reference?: string,
    public referenceProvider?: string,
    public name?: string,
    public quantity?: number,
    public purchaseUnitPrice?: number,
    public saleUnitPrice?: number,
    public stocklimit?: number,
    public stocklimitAlert?: boolean,
    public unitType?: ProductUnitEnum,
    public size?: Size,
    public color?: string,
    public imageContentType?: string,
    public image?: any,
    public isDisplayedInCashier?: boolean,
    public movements?: IMovement[],
    public taxItems?: ITaxItem[],
    public tvaItems?: ITVAItem[],
    public productCategory?: IProductCategory,
    public productMark?: IProductMark,
    public depot?: IDepot
  ) {
    this.stocklimitAlert = this.stocklimitAlert || false;
    this.isDisplayedInCashier = this.isDisplayedInCashier || false;
  }
}
