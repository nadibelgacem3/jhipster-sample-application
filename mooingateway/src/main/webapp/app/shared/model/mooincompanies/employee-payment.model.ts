import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/mooincompanies/employee.model';

export interface IEmployeePayment {
  id?: number;
  details?: string;
  amount?: number;
  paymentDate?: Moment;
  fromDate?: Moment;
  toDate?: Moment;
  imageJustifContentType?: string;
  imageJustif?: any;
  employee?: IEmployee;
}

export class EmployeePayment implements IEmployeePayment {
  constructor(
    public id?: number,
    public details?: string,
    public amount?: number,
    public paymentDate?: Moment,
    public fromDate?: Moment,
    public toDate?: Moment,
    public imageJustifContentType?: string,
    public imageJustif?: any,
    public employee?: IEmployee
  ) {}
}
