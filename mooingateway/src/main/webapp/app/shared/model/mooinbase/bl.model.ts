import { Moment } from 'moment';
import { IBLItem } from 'app/shared/model/mooinbase/bl-item.model';
import { ITiers } from 'app/shared/model/mooinbase/tiers.model';
import { BLStatusEnum } from 'app/shared/model/enumerations/bl-status-enum.model';
import { BLTypeEnum } from 'app/shared/model/enumerations/bl-type-enum.model';

export interface IBL {
  id?: number;
  number?: string;
  reference?: string;
  status?: BLStatusEnum;
  type?: BLTypeEnum;
  totalHT?: number;
  totalTVA?: number;
  totaTTC?: number;
  date?: Moment;
  dueDate?: Moment;
  isConverted?: boolean;
  companyID?: number;
  blItems?: IBLItem[];
  tiers?: ITiers;
}

export class BL implements IBL {
  constructor(
    public id?: number,
    public number?: string,
    public reference?: string,
    public status?: BLStatusEnum,
    public type?: BLTypeEnum,
    public totalHT?: number,
    public totalTVA?: number,
    public totaTTC?: number,
    public date?: Moment,
    public dueDate?: Moment,
    public isConverted?: boolean,
    public companyID?: number,
    public blItems?: IBLItem[],
    public tiers?: ITiers
  ) {
    this.isConverted = this.isConverted || false;
  }
}
