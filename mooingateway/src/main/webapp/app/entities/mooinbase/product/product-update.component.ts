import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProduct, Product } from 'app/shared/model/mooinbase/product.model';
import { ProductService } from './product.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProductCategory } from 'app/shared/model/mooinbase/product-category.model';
import { ProductCategoryService } from 'app/entities/mooinbase/product-category/product-category.service';
import { IProductMark } from 'app/shared/model/mooinbase/product-mark.model';
import { ProductMarkService } from 'app/entities/mooinbase/product-mark/product-mark.service';
import { IDepot } from 'app/shared/model/mooinbase/depot.model';
import { DepotService } from 'app/entities/mooinbase/depot/depot.service';

type SelectableEntity = IProductCategory | IProductMark | IDepot;

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  productcategories: IProductCategory[] = [];
  productmarks: IProductMark[] = [];
  depots: IDepot[] = [];

  editForm = this.fb.group({
    id: [],
    companyID: [],
    reference: [],
    referenceProvider: [],
    name: [null, [Validators.required]],
    quantity: [null, [Validators.required]],
    purchaseUnitPrice: [null, [Validators.required, Validators.min(0)]],
    saleUnitPrice: [null, [Validators.required, Validators.min(0)]],
    stocklimit: [],
    stocklimitAlert: [],
    unitType: [],
    size: [],
    color: [],
    image: [],
    imageContentType: [],
    isDisplayedInCashier: [],
    productCategory: [],
    productMark: [],
    depot: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected productService: ProductService,
    protected productCategoryService: ProductCategoryService,
    protected productMarkService: ProductMarkService,
    protected depotService: DepotService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);

      this.productCategoryService.query().subscribe((res: HttpResponse<IProductCategory[]>) => (this.productcategories = res.body || []));

      this.productMarkService.query().subscribe((res: HttpResponse<IProductMark[]>) => (this.productmarks = res.body || []));

      this.depotService.query().subscribe((res: HttpResponse<IDepot[]>) => (this.depots = res.body || []));
    });
  }

  updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      companyID: product.companyID,
      reference: product.reference,
      referenceProvider: product.referenceProvider,
      name: product.name,
      quantity: product.quantity,
      purchaseUnitPrice: product.purchaseUnitPrice,
      saleUnitPrice: product.saleUnitPrice,
      stocklimit: product.stocklimit,
      stocklimitAlert: product.stocklimitAlert,
      unitType: product.unitType,
      size: product.size,
      color: product.color,
      image: product.image,
      imageContentType: product.imageContentType,
      isDisplayedInCashier: product.isDisplayedInCashier,
      productCategory: product.productCategory,
      productMark: product.productMark,
      depot: product.depot,
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
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      companyID: this.editForm.get(['companyID'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      referenceProvider: this.editForm.get(['referenceProvider'])!.value,
      name: this.editForm.get(['name'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      purchaseUnitPrice: this.editForm.get(['purchaseUnitPrice'])!.value,
      saleUnitPrice: this.editForm.get(['saleUnitPrice'])!.value,
      stocklimit: this.editForm.get(['stocklimit'])!.value,
      stocklimitAlert: this.editForm.get(['stocklimitAlert'])!.value,
      unitType: this.editForm.get(['unitType'])!.value,
      size: this.editForm.get(['size'])!.value,
      color: this.editForm.get(['color'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      isDisplayedInCashier: this.editForm.get(['isDisplayedInCashier'])!.value,
      productCategory: this.editForm.get(['productCategory'])!.value,
      productMark: this.editForm.get(['productMark'])!.value,
      depot: this.editForm.get(['depot'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
