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
  
  constructor(protected service: ProductComponentService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
  }

  ngAfterContentInit():void {
    this.getComponents();
  }

  getComponents = (): Promise<ProductComponent[]> => this.service.getItens().then(data => this.data = (data !== null ? data as ProductComponent[] : new Array<ProductComponent>()));
  selectRecord = (record): ProductComponent => this.selectedRecord = record;
  search = (): void => alert('Não implementado.');
  
  clickEdit = (event: Event, record: ProductComponent) => { 
    console.log("click edit: " + event);
    event.stopImmediatePropagation(); 
    this.openRecord(record) 
  };
  
  clickDelete = (event: Event, record: ProductComponent) => { 
    console.log("click delete: " + event);
    event.stopImmediatePropagation(); 
    this.service.remove(record.id).then(
      () => this.getComponents()
    )
  };

  openRecord(record): void {
    console.log(JSON.stringify(record));
    this.selectRecord(record);
    this.router.navigate(["componentes/componente/", this.selectedRecord.id]);
  }

}