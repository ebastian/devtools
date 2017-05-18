import { Component, OnInit, AfterContentInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Product } from '../service/product';

@Component({
  selector: 'app-product-tab',
  templateUrl: './product-tab.component.html',
  styleUrls: ['./product-tab.component.css']
})
export class ProductTabComponent implements OnInit, AfterContentInit {

  @Input()
  product: Product = new Product();

  constructor(private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void { }
  ngAfterContentInit(): void { }

  clickActive = () => this.product.death = null;
  clickDesactive = () => this.product.death = '09/06/1986';

}