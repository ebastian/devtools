import { Component, OnInit, AfterContentInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ProductComponent } from '../service/product-component';

@Component({
  selector: 'app-products-components-tab',
  templateUrl: './products-components-tab.component.html',
  styleUrls: ['./products-components-tab.component.css']
})
export class ProductsComponentsTabComponent implements OnInit {

  @Input()
  productComponent: ProductComponent = new ProductComponent();

  constructor(private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void { }
  ngAfterContentInit(): void { }

  clickActive = () => this.productComponent.death = null;
  clickDesactive = () => this.productComponent.death = '09/06/1986';

}