import { ICompany } from 'app/shared/model/mooincompanies/company.model';

export interface ITVA {
  id?: number;
  name?: string;
  percentageValue?: number;
  company?: ICompany;
}

export class TVA implements ITVA {
  constructor(public id?: number, public name?: string, public percentageValue?: number, public company?: ICompany) {}
}
