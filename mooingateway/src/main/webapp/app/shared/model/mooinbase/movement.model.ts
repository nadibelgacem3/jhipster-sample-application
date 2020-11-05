import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/mooinbase/product.model';
import { MovementTypeEnum } from 'app/shared/model/enumerations/movement-type-enum.model';
import { MovementReasonEnum } from 'app/shared/model/enumerations/movement-reason-enum.model';

export interface IMovement {
  id?: number;
  type?: MovementTypeEnum;
  reason?: MovementReasonEnum;
  date?: Moment;
  billID?: number;
  tiersID?: number;
  quantity?: number;
  companyUserID?: number;
  product?: IProduct;
}

export class Movement implements IMovement {
  constructor(
    public id?: number,
    public type?: MovementTypeEnum,
    public reason?: MovementReasonEnum,
    public date?: Moment,
    public billID?: number,
    public tiersID?: number,
    public quantity?: number,
    public companyUserID?: number,
    public product?: IProduct
  ) {}
}
