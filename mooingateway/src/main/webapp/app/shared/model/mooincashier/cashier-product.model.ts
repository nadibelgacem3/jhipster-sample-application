export interface ICashierProduct {
  id?: number;
  productID?: number;
  cashierProdName?: string;
  cashierProdQty?: number;
  cashierProdPurchaseUnitPrice?: number;
  cashierProdSaleUnitPrice?: number;
  cashierProdStockLimit?: number;
  cashierProdStockLimitAlert?: boolean;
}

export class CashierProduct implements ICashierProduct {
  constructor(
    public id?: number,
    public productID?: number,
    public cashierProdName?: string,
    public cashierProdQty?: number,
    public cashierProdPurchaseUnitPrice?: number,
    public cashierProdSaleUnitPrice?: number,
    public cashierProdStockLimit?: number,
    public cashierProdStockLimitAlert?: boolean
  ) {
    this.cashierProdStockLimitAlert = this.cashierProdStockLimitAlert || false;
  }
}
