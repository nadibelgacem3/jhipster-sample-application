import { IProduct } from 'app/shared/model/mooinbase/product.model';

export interface IProductCategory {
  id?: number;
  name?: string;
  description?: string;
  companyID?: number;
  products?: IProduct[];
}

export class ProductCategory implements IProductCategory {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public companyID?: number,
    public products?: IProduct[]
  ) {}
}
