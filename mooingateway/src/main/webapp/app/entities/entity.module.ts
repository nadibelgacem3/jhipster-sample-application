import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company',
        loadChildren: () => import('./mooincompanies/company/company.module').then(m => m.MooincompaniesCompanyModule),
      },
      {
        path: 'company-bank-account',
        loadChildren: () =>
          import('./mooincompanies/company-bank-account/company-bank-account.module').then(m => m.MooincompaniesCompanyBankAccountModule),
      },
      {
        path: 'company-module',
        loadChildren: () => import('./mooincompanies/company-module/company-module.module').then(m => m.MooincompaniesCompanyModuleModule),
      },
      {
        path: 'company-bill-payment',
        loadChildren: () =>
          import('./mooincompanies/company-bill-payment/company-bill-payment.module').then(m => m.MooincompaniesCompanyBillPaymentModule),
      },
      {
        path: 'company-bill-payment-item',
        loadChildren: () =>
          import('./mooincompanies/company-bill-payment-item/company-bill-payment-item.module').then(
            m => m.MooincompaniesCompanyBillPaymentItemModule
          ),
      },
      {
        path: 'employee',
        loadChildren: () => import('./mooincompanies/employee/employee.module').then(m => m.MooincompaniesEmployeeModule),
      },
      {
        path: 'employee-payment',
        loadChildren: () =>
          import('./mooincompanies/employee-payment/employee-payment.module').then(m => m.MooincompaniesEmployeePaymentModule),
      },
      {
        path: 'company-location',
        loadChildren: () =>
          import('./mooincompanies/company-location/company-location.module').then(m => m.MooincompaniesCompanyLocationModule),
      },
      {
        path: 'employee-location',
        loadChildren: () =>
          import('./mooincompanies/employee-location/employee-location.module').then(m => m.MooincompaniesEmployeeLocationModule),
      },
      {
        path: 'transaction-comp',
        loadChildren: () =>
          import('./mooincompanies/transaction-comp/transaction-comp.module').then(m => m.MooincompaniesTransactionCompModule),
      },
      {
        path: 'tva',
        loadChildren: () => import('./mooincompanies/tva/tva.module').then(m => m.MooincompaniesTVAModule),
      },
      {
        path: 'tax',
        loadChildren: () => import('./mooincompanies/tax/tax.module').then(m => m.MooincompaniesTaxModule),
      },
      {
        path: 'depot',
        loadChildren: () => import('./mooinbase/depot/depot.module').then(m => m.MooinbaseDepotModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./mooinbase/product/product.module').then(m => m.MooinbaseProductModule),
      },
      {
        path: 'product-category',
        loadChildren: () => import('./mooinbase/product-category/product-category.module').then(m => m.MooinbaseProductCategoryModule),
      },
      {
        path: 'product-mark',
        loadChildren: () => import('./mooinbase/product-mark/product-mark.module').then(m => m.MooinbaseProductMarkModule),
      },
      {
        path: 'tva-item',
        loadChildren: () => import('./mooinbase/tva-item/tva-item.module').then(m => m.MooinbaseTVAItemModule),
      },
      {
        path: 'tax-item',
        loadChildren: () => import('./mooinbase/tax-item/tax-item.module').then(m => m.MooinbaseTaxItemModule),
      },
      {
        path: 'movement',
        loadChildren: () => import('./mooinbase/movement/movement.module').then(m => m.MooinbaseMovementModule),
      },
      {
        path: 'invoice',
        loadChildren: () => import('./mooinbase/invoice/invoice.module').then(m => m.MooinbaseInvoiceModule),
      },
      {
        path: 'invoice-item',
        loadChildren: () => import('./mooinbase/invoice-item/invoice-item.module').then(m => m.MooinbaseInvoiceItemModule),
      },
      {
        path: 'invoice-payment-history',
        loadChildren: () =>
          import('./mooinbase/invoice-payment-history/invoice-payment-history.module').then(m => m.MooinbaseInvoicePaymentHistoryModule),
      },
      {
        path: 'quote',
        loadChildren: () => import('./mooinbase/quote/quote.module').then(m => m.MooinbaseQuoteModule),
      },
      {
        path: 'quote-item',
        loadChildren: () => import('./mooinbase/quote-item/quote-item.module').then(m => m.MooinbaseQuoteItemModule),
      },
      {
        path: 'bl',
        loadChildren: () => import('./mooinbase/bl/bl.module').then(m => m.MooinbaseBLModule),
      },
      {
        path: 'bl-item',
        loadChildren: () => import('./mooinbase/bl-item/bl-item.module').then(m => m.MooinbaseBLItemModule),
      },
      {
        path: 'avoir',
        loadChildren: () => import('./mooinbase/avoir/avoir.module').then(m => m.MooinbaseAvoirModule),
      },
      {
        path: 'avoir-item',
        loadChildren: () => import('./mooinbase/avoir-item/avoir-item.module').then(m => m.MooinbaseAvoirItemModule),
      },
      {
        path: 'tiers',
        loadChildren: () => import('./mooinbase/tiers/tiers.module').then(m => m.MooinbaseTiersModule),
      },
      {
        path: 'tiers-location',
        loadChildren: () => import('./mooinbase/tiers-location/tiers-location.module').then(m => m.MooinbaseTiersLocationModule),
      },
      {
        path: 'tiers-bank-check',
        loadChildren: () => import('./mooinbase/tiers-bank-check/tiers-bank-check.module').then(m => m.MooinbaseTiersBankCheckModule),
      },
      {
        path: 'cashier',
        loadChildren: () => import('./mooincashier/cashier/cashier.module').then(m => m.MooincashierCashierModule),
      },
      {
        path: 'cashier-product',
        loadChildren: () => import('./mooincashier/cashier-product/cashier-product.module').then(m => m.MooincashierCashierProductModule),
      },
      {
        path: 'cashier-location',
        loadChildren: () =>
          import('./mooincashier/cashier-location/cashier-location.module').then(m => m.MooincashierCashierLocationModule),
      },
      {
        path: 'cashier-costumer',
        loadChildren: () =>
          import('./mooincashier/cashier-costumer/cashier-costumer.module').then(m => m.MooincashierCashierCostumerModule),
      },
      {
        path: 'cashier-receipt',
        loadChildren: () => import('./mooincashier/cashier-receipt/cashier-receipt.module').then(m => m.MooincashierCashierReceiptModule),
      },
      {
        path: 'cashier-receipt-item',
        loadChildren: () =>
          import('./mooincashier/cashier-receipt-item/cashier-receipt-item.module').then(m => m.MooincashierCashierReceiptItemModule),
      },
      {
        path: 'cashier-receipt-pay',
        loadChildren: () =>
          import('./mooincashier/cashier-receipt-pay/cashier-receipt-pay.module').then(m => m.MooincashierCashierReceiptPayModule),
      },
      {
        path: 'cashier-appro',
        loadChildren: () => import('./mooincashier/cashier-appro/cashier-appro.module').then(m => m.MooincashierCashierApproModule),
      },
      {
        path: 'cashier-appro-item',
        loadChildren: () =>
          import('./mooincashier/cashier-appro-item/cashier-appro-item.module').then(m => m.MooincashierCashierApproItemModule),
      },
      {
        path: 'offer',
        loadChildren: () => import('./mooinecommerce/offer/offer.module').then(m => m.MooinecommerceOfferModule),
      },
      {
        path: 'offer-order',
        loadChildren: () => import('./mooinecommerce/offer-order/offer-order.module').then(m => m.MooinecommerceOfferOrderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MooingatewayEntityModule {}
