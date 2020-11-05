import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICashierReceiptPay, CashierReceiptPay } from 'app/shared/model/mooincashier/cashier-receipt-pay.model';
import { CashierReceiptPayService } from './cashier-receipt-pay.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICashierReceipt } from 'app/shared/model/mooincashier/cashier-receipt.model';
import { CashierReceiptService } from 'app/entities/mooincashier/cashier-receipt/cashier-receipt.service';

@Component({
  selector: 'jhi-cashier-receipt-pay-update',
  templateUrl: './cashier-receipt-pay-update.component.html',
})
export class CashierReceiptPayUpdateComponent implements OnInit {
  isSaving = false;
  cashierreceipts: ICashierReceipt[] = [];

  editForm = this.fb.group({
    id: [],
    details: [],
    amount: [null, [Validators.required, Validators.min(0)]],
    paymentDate: [],
    paymentMethod: [],
    bankCheckorTraitNumber: [],
    imageJustif: [],
    imageJustifContentType: [],
    cashierReceipt: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected cashierReceiptPayService: CashierReceiptPayService,
    protected cashierReceiptService: CashierReceiptService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cashierReceiptPay }) => {
      if (!cashierReceiptPay.id) {
        const today = moment().startOf('day');
        cashierReceiptPay.paymentDate = today;
      }

      this.updateForm(cashierReceiptPay);

      this.cashierReceiptService.query().subscribe((res: HttpResponse<ICashierReceipt[]>) => (this.cashierreceipts = res.body || []));
    });
  }

  updateForm(cashierReceiptPay: ICashierReceiptPay): void {
    this.editForm.patchValue({
      id: cashierReceiptPay.id,
      details: cashierReceiptPay.details,
      amount: cashierReceiptPay.amount,
      paymentDate: cashierReceiptPay.paymentDate ? cashierReceiptPay.paymentDate.format(DATE_TIME_FORMAT) : null,
      paymentMethod: cashierReceiptPay.paymentMethod,
      bankCheckorTraitNumber: cashierReceiptPay.bankCheckorTraitNumber,
      imageJustif: cashierReceiptPay.imageJustif,
      imageJustifContentType: cashierReceiptPay.imageJustifContentType,
      cashierReceipt: cashierReceiptPay.cashierReceipt,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('mooingatewayApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cashierReceiptPay = this.createFromForm();
    if (cashierReceiptPay.id !== undefined) {
      this.subscribeToSaveResponse(this.cashierReceiptPayService.update(cashierReceiptPay));
    } else {
      this.subscribeToSaveResponse(this.cashierReceiptPayService.create(cashierReceiptPay));
    }
  }

  private createFromForm(): ICashierReceiptPay {
    return {
      ...new CashierReceiptPay(),
      id: this.editForm.get(['id'])!.value,
      details: this.editForm.get(['details'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      paymentDate: this.editForm.get(['paymentDate'])!.value
        ? moment(this.editForm.get(['paymentDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      bankCheckorTraitNumber: this.editForm.get(['bankCheckorTraitNumber'])!.value,
      imageJustifContentType: this.editForm.get(['imageJustifContentType'])!.value,
      imageJustif: this.editForm.get(['imageJustif'])!.value,
      cashierReceipt: this.editForm.get(['cashierReceipt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICashierReceiptPay>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICashierReceipt): any {
    return item.id;
  }
}
