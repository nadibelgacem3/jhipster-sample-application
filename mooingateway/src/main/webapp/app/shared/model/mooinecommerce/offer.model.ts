import { Moment } from 'moment';
import { IOfferOrder } from 'app/shared/model/mooinecommerce/offer-order.model';
import { OfferStatus } from 'app/shared/model/enumerations/offer-status.model';

export interface IOffer {
  id?: number;
  name?: string;
  description?: string;
  imageContentType?: string;
  image?: any;
  dateBegin?: Moment;
  dateEnd?: Moment;
  duration?: number;
  status?: OfferStatus;
  productId?: number;
  saleUnitPriceBeforeDiscount?: number;
  discountRate?: number;
  saleUnitPriceAfterDiscount?: number;
  withTVA?: boolean;
  withTax?: boolean;
  isActivated?: boolean;
  companyID?: number;
  offerOrders?: IOfferOrder[];
}

export class Offer implements IOffer {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public imageContentType?: string,
    public image?: any,
    public dateBegin?: Moment,
    public dateEnd?: Moment,
    public duration?: number,
    public status?: OfferStatus,
    public productId?: number,
    public saleUnitPriceBeforeDiscount?: number,
    public discountRate?: number,
    public saleUnitPriceAfterDiscount?: number,
    public withTVA?: boolean,
    public withTax?: boolean,
    public isActivated?: boolean,
    public companyID?: number,
    public offerOrders?: IOfferOrder[]
  ) {
    this.withTVA = this.withTVA || false;
    this.withTax = this.withTax || false;
    this.isActivated = this.isActivated || false;
  }
}
