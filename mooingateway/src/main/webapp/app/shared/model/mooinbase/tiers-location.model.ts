import { ITiers } from 'app/shared/model/mooinbase/tiers.model';

export interface ITiersLocation {
  id?: number;
  localNumber?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  countryName?: string;
  flagContentType?: string;
  flag?: any;
  tiers?: ITiers;
}

export class TiersLocation implements ITiersLocation {
  constructor(
    public id?: number,
    public localNumber?: string,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public stateProvince?: string,
    public countryName?: string,
    public flagContentType?: string,
    public flag?: any,
    public tiers?: ITiers
  ) {}
}
