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

  component: ProductComponent = new ProductComponent();

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
  assignProduct = componentLoaded => this.component = (componentLoaded !== null && componentLoaded !== undefined ? componentLoaded as ProductComponent : new ProductComponent());
  logProductLoaded = () => console.log("ProductComponent loaded: " + JSON.stringify(this.component));
  openComponents = () => this.component.id !== undefined ? this.selectTab('components') : '';

  setBusy = (busy: boolean) => this.busy = busy;
  unBusy = () => this.setBusy(false);

  selectTab = (abaId: string): string => this.selectedTab = abaId;

  clickSave = (component: ProductComponent): void => {
    this.service.save(component).then(this.saveResponse);
  };

  saveResponse = (comp:ProductComponent) => {
    this.saveAndContinue ? this.assignProduct(comp) : this.showProductList();
  }

  clickDelete = (component: ProductComponent): void => { this.service.remove(component.id); this.showProductList(); };
  clickClear = () => this.component = new ProductComponent();
  clickToggleActive = (component: ProductComponent): void => {
    if(component.death === null || component.death === undefined) {
      this.service.kill(component).then(this.toggleActiveResponse('kill'));
    } else {
      this.service.revive(component).then(this.toggleActiveResponse('revive'));
    }
  };

  toggleActiveResponse = action => (success: boolean): void => {
      console.log(action + ' -> Ok? ' + success);
      this.loadProductComponent(this.component.id);
  }

  showProductList = (): Promise<boolean> => this.router.navigate(["./componentes"]);

}