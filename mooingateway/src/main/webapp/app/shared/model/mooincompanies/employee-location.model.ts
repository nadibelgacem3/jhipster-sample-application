import { IEmployee } from 'app/shared/model/mooincompanies/employee.model';

export interface IEmployeeLocation {
  id?: number;
  localNumber?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  countryName?: string;
  flagContentType?: string;
  flag?: any;
  employee?: IEmployee;
}

export class EmployeeLocation implements IEmployeeLocation {
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
    public employee?: IEmployee
  ) {}
}
