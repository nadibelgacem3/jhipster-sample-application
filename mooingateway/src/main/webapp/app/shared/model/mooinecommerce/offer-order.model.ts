import { Moment } from 'moment';
import { IOffer } from 'app/shared/model/mooinecommerce/offer.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';

export interface IOfferOrder {
  id?: number;
  quantity?: number;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  date?: Moment;
  status?: OrderStatus;
  requesterOfferID?: number;
  offer?: IOffer;
}

export class OfferOrder implements IOfferOrder {
  constructor(
    public id?: number,
    public quantity?: number,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public date?: Moment,
    public status?: OrderStatus,
    public requesterOfferID?: number,
    public offer?: IOffer
  ) {}
}
