import { IProduct } from 'app/shared/model/mooinbase/product.model';

export interface IDepot {
  id?: number;
  name?: string;
  details?: string;
  location?: string;
  companyID?: number;
  products?: IProduct[];
}

export class Depot implements IDepot {
  constructor(
    public id?: number,
    public name?: string,
    public details?: string,
    public location?: string,
    public companyID?: number,
    public products?: IProduct[]
  ) {}
}
