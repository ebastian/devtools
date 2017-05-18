import { Component, OnInit, AfterContentInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Router } from '@angular/router';

import { ProductService } from '../service/product.service';

import { Product } from '../service/product';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css'],
  providers: [
    ProductService
  ]
})
export class ProductFormComponent implements OnInit, AfterContentInit {

  selectedTab: string;

  record: Product = new Product();

  busy: boolean = false;

  constructor(protected service: ProductService, protected route: ActivatedRoute, protected router: Router) { 
  }

  ngOnInit(): void {
    this.selectTab('product');
  }

  ngAfterContentInit(): void {
    this.route.params.forEach((params: Params) => {
      let id = +params['id'];
      if (!isNaN(id)) {
        this.loadProduct(id);
      } else {
        this.setBusy(false);
      }
    });
  }
  
  loadProduct = (id: number) => this.service.getItem(id).then(this.assignProduct).then(this.logProductLoaded).then(this.unBusy).then(this.openComponents);
  assignProduct = recordLoaded => this.record = (recordLoaded !== null && recordLoaded !== undefined ? recordLoaded as Product : new Product());
  logProductLoaded = () => console.log("Product loaded: " + JSON.stringify(this.record));
  openComponents = () => this.record.id !== undefined ? this.selectTab('components') : '';

  assignCreation = (record: Product) => record.creation = (record.creation === undefined ? '01/01/2017' : record.creation);

  setBusy = (busy: boolean) => this.busy = busy;
  unBusy = () => this.setBusy(false);

  selectTab = (abaId: string): string => this.selectedTab = abaId;
  
  //this.openComponents()
  clickSave = (record: Product): void => { this.assignCreation(record); this.service.save(record); this.showProductList(); };
  clickDelete = (record: Product): void => { this.service.remove(record.id); this.showProductList(); };

  showProductList = (): Promise<boolean> => this.router.navigate(["./produtos"]);

}
