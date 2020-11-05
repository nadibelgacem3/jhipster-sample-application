import { IProduct } from 'app/shared/model/mooinbase/product.model';

export interface ITaxItem {
  id?: number;
  name?: string;
  isValued?: boolean;
  isPercentage?: boolean;
  value?: number;
  companyID?: number;
  taxID?: number;
  product?: IProduct;
}

export class TaxItem implements ITaxItem {
  constructor(
    public id?: number,
    public name?: string,
    public isValued?: boolean,
    public isPercentage?: boolean,
    public value?: number,
    public companyID?: number,
    public taxID?: number,
    public product?: IProduct
  ) {
    this.isValued = this.isValued || false;
    this.isPercentage = this.isPercentage || false;
  }
}
