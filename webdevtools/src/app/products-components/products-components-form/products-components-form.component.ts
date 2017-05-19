import { Component, OnInit, AfterContentInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { ProductComponentService } from '../service/product-component.service';
import { ProductComponent } from '../service/product-component';

@Component({
  selector: 'app-products-components-form',
  templateUrl: './products-components-form.component.html',
  styleUrls: ['./products-components-form.component.css'],
  providers: [
    ProductComponentService
  ]
})
export class ProductsComponentsFormComponent implements OnInit {

  selectedTab: string;

  record: ProductComponent = new ProductComponent();

  busy: boolean = false;
  saveAndContinue: boolean = false;

  constructor(protected service: ProductComponentService, protected route: ActivatedRoute, protected router: Router) {
  }

  ngOnInit(): void {
    this.selectTab('component');
  }

  ngAfterContentInit(): void {
    this.route.params.forEach((params: Params) => {
      let id = +params['id'];
      if (!isNaN(id)) {
        this.loadProductComponent(id);
      } else {
        this.setBusy(false);
      }
    });
  }

  loadProductComponent = (id: number) => this.service.getItem(id).then(this.assignProduct).then(this.logProductLoaded).then(this.unBusy).then(this.openComponents);
  assignProduct = recordLoaded => this.record = (recordLoaded !== null && recordLoaded !== undefined ? recordLoaded as ProductComponent : new ProductComponent());
  logProductLoaded = () => console.log("ProductComponent loaded: " + JSON.stringify(this.record));
  openComponents = () => this.record.id !== undefined ? this.selectTab('components') : '';

  setCreation = (creationDate: Date) => (record: ProductComponent) => record.creation = (record.creation === undefined ? ("0" + creationDate.getDate()).slice(-2) + "/" + ("0" + (creationDate.getMonth() + 1)).slice(-2) + "/" + creationDate.getFullYear() : record.creation);

  setBusy = (busy: boolean) => this.busy = busy;
  unBusy = () => this.setBusy(false);

  selectTab = (abaId: string): string => this.selectedTab = abaId;

  //this.openComponents()
  clickSave = (record: ProductComponent): void => {
    this.setCreation(new Date())(record);
    this.service.save(record);
    if (!this.saveAndContinue) {
      this.showProductList();
    }
  };
  clickDelete = (record: ProductComponent): void => { this.service.remove(record.id); this.showProductList(); };
  clickClear = () => this.record = new ProductComponent();

  showProductList = (): Promise<boolean> => this.router.navigate(["./componentes"]);

}