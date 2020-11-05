import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MooingatewaySharedModule } from 'app/shared/shared.module';
import { QuoteItemComponent } from './quote-item.component';
import { QuoteItemDetailComponent } from './quote-item-detail.component';
import { QuoteItemUpdateComponent } from './quote-item-update.component';
import { QuoteItemDeleteDialogComponent } from './quote-item-delete-dialog.component';
import { quoteItemRoute } from './quote-item.route';

@NgModule({
  imports: [MooingatewaySharedModule, RouterModule.forChild(quoteItemRoute)],
  declarations: [QuoteItemComponent, QuoteItemDetailComponent, QuoteItemUpdateComponent, QuoteItemDeleteDialogComponent],
  entryComponents: [QuoteItemDeleteDialogComponent],
})
export class MooinbaseQuoteItemModule {}
