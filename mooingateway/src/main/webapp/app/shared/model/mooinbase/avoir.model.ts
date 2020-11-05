import { Moment } from 'moment';
import { IAvoirItem } from 'app/shared/model/mooinbase/avoir-item.model';
import { ITiers } from 'app/shared/model/mooinbase/tiers.model';
import { AvoirStatusEnum } from 'app/shared/model/enumerations/avoir-status-enum.model';
import { AvoirTypeEnum } from 'app/shared/model/enumerations/avoir-type-enum.model';

export interface IAvoir {
  id?: number;
  number?: string;
  reference?: string;
  status?: AvoirStatusEnum;
  type?: AvoirTypeEnum;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  date?: Moment;
  dueDate?: Moment;
  isConverted?: boolean;
  companyID?: number;
  avoirItems?: IAvoirItem[];
  tiers?: ITiers;
}

export class Avoir implements IAvoir {
  constructor(
    public id?: number,
    public number?: string,
    public reference?: string,
    public status?: AvoirStatusEnum,
    public type?: AvoirTypeEnum,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public date?: Moment,
    public dueDate?: Moment,
    public isConverted?: boolean,
    public companyID?: number,
    public avoirItems?: IAvoirItem[],
    public tiers?: ITiers
  ) {
    this.isConverted = this.isConverted || false;
  }
}
