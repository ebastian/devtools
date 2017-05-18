import { Component, Input, OnInit } from '@angular/core';

import { ProductComponent } from '../../products-components/service/product-component';

@Component({
  selector: 'app-product-components-tab',
  templateUrl: './product-components-tab.component.html',
  styleUrls: ['./product-components-tab.component.css']
})
export class ProductComponentsTabComponent implements OnInit {

  @Input()
  productComponents: Array<ProductComponent>;

  component: ProductComponent;

  constructor() { 
    this.productComponents = Array<ProductComponent>();
    this.component = new ProductComponent();
  }

  ngOnInit() {
    console.log(JSON.stringify(this.productComponents));
  }

  clickSave = (component: ProductComponent) => { 
    this.productComponents.push(component);
    this.component = new ProductComponent();
  }

}
