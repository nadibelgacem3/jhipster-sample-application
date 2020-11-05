import { Moment } from 'moment';
import { IQuoteItem } from 'app/shared/model/mooinbase/quote-item.model';
import { ITiers } from 'app/shared/model/mooinbase/tiers.model';
import { QuoteStatusEnum } from 'app/shared/model/enumerations/quote-status-enum.model';
import { QuoteTypeEnum } from 'app/shared/model/enumerations/quote-type-enum.model';

export interface IQuote {
  id?: number;
  number?: string;
  reference?: string;
  status?: QuoteStatusEnum;
  type?: QuoteTypeEnum;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  date?: Moment;
  dueDate?: Moment;
  isConverted?: boolean;
  companyID?: number;
  quoteitems?: IQuoteItem[];
  tiers?: ITiers;
}

export class Quote implements IQuote {
  constructor(
    public id?: number,
    public number?: string,
    public reference?: string,
    public status?: QuoteStatusEnum,
    public type?: QuoteTypeEnum,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public date?: Moment,
    public dueDate?: Moment,
    public isConverted?: boolean,
    public companyID?: number,
    public quoteitems?: IQuoteItem[],
    public tiers?: ITiers
  ) {
    this.isConverted = this.isConverted || false;
  }
}
