import { Component, OnInit, AfterContentInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { ProductComponent } from '../service/product-component';
import { ProductComponentService } from '../service/product-component.service';

@Component({
  selector: 'app-products-components-list',
  templateUrl: './products-components-list.component.html',
  styleUrls: ['./products-components-list.component.css'],
  providers: [
    ProductComponentService
  ]
})
export class ProductsComponentsListComponent implements OnInit {

  data: ProductComponent[] = [];

  selectedRecord: ProductComponent = null;

  busy = false;

  constructor(protected service: ProductComponentService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
  }

  ngAfterContentInit():void {
    this.service.getItens().then(data => this.data = (data !== null ? data as ProductComponent[] : new Array<ProductComponent>())).then(() => this.busy = false);
    /*
    this.route.params.forEach((params: Params) => {
      let id = +params['id'];
      if(!isNaN(id)) {
        console.log('SELECT ' + id);
        this.service.getItem(id).then(registry => this.selectRecord(registry));
      }
    });
    */
  }

  selectRecord = (record): ProductComponent => this.selectedRecord = record;
  clickEdit = (eventw: Event, record: ProductComponent) => { event.stopPropagation(); this.openRecord(record) };
  clickDelete = (event: Event, record: ProductComponent) => { event.stopPropagation(); this.service.remove(record.id) };

  openRecord(record): void {
    this.selectRecord(record);
    this.router.navigate(["componentes/componente/", this.selectedRecord.id]);
  }

}