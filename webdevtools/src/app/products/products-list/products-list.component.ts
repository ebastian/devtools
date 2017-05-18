import { Component, OnInit, AfterContentInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Product } from '../service/product';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css'],
  providers: [
    ProductService
  ]
})
export class ProductsListComponent implements OnInit {

  data: Product[] = [];

  selectedRecord: Product = null;

  busy = false;

  constructor(protected service: ProductService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
  }

  ngAfterContentInit():void {
    this.service.getItens().then(data => this.data = (data !== null ? data as Product[] : new Array<Product>())).then(() => this.busy = false);
  }

  selectRecord = (record): Product => this.selectedRecord = record;
  clickEdit = (event: Event, record: Product) => { event.stopPropagation(); this.openRecord(record) };
  clickDelete = (event: Event, record: Product) => { event.stopPropagation(); this.service.remove(record.id) };

  openRecord(record): void {
    this.selectRecord(record);
    this.router.navigate(["produtos/produto/", this.selectedRecord.id]);
  }

}