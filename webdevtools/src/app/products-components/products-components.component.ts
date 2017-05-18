import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-products-components',
  templateUrl: '../shared/templates/panel-template.html',
  styleUrls: ['./products-components.component.css']
})
export class ProductsComponentsComponent implements OnInit {

  title: string = 'Componentes';

  constructor() { }

  ngOnInit() {
  }

}
