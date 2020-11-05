import { IProduct } from 'app/shared/model/mooinbase/product.model';
import { IBL } from 'app/shared/model/mooinbase/bl.model';

export interface IBLItem {
  id?: number;
  quantity?: number;
  discountRate?: number;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  product?: IProduct;
  bl?: IBL;
}

export class BLItem implements IBLItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public discountRate?: number,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public product?: IProduct,
    public bl?: IBL
  ) {}
}
