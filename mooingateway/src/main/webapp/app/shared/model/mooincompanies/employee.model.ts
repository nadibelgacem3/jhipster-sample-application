import { Moment } from 'moment';
import { IEmployeeLocation } from 'app/shared/model/mooincompanies/employee-location.model';
import { IEmployeePayment } from 'app/shared/model/mooincompanies/employee-payment.model';
import { ICompany } from 'app/shared/model/mooincompanies/company.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  jobTitle?: string;
  gender?: Gender;
  dateOfborth?: Moment;
  dateOfRecruitment?: Moment;
  imageContentType?: string;
  image?: any;
  email?: string;
  phoneNumber?: string;
  salary?: number;
  commissionPct?: number;
  rates?: number;
  employeeLocation?: IEmployeeLocation;
  employeePayments?: IEmployeePayment[];
  company?: ICompany;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public jobTitle?: string,
    public gender?: Gender,
    public dateOfborth?: Moment,
    public dateOfRecruitment?: Moment,
    public imageContentType?: string,
    public image?: any,
    public email?: string,
    public phoneNumber?: string,
    public salary?: number,
    public commissionPct?: number,
    public rates?: number,
    public employeeLocation?: IEmployeeLocation,
    public employeePayments?: IEmployeePayment[],
    public company?: ICompany
  ) {}
}
