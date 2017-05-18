import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductsComponent } from './products.component';
import { ProductsListComponent } from './products-list/products-list.component';
import { ProductFormComponent } from './product-form/product-form.component';
import { ProductTabComponent } from './product-tab/product-tab.component';
import { ProductComponentsTabComponent } from './product-components-tab/product-components-tab.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
  declarations: [
    ProductsComponent, 
    ProductsListComponent, 
    ProductFormComponent, 
    ProductTabComponent, 
    ProductComponentsTabComponent
  ],
  exports: [ProductsComponent]
})
export class ProductsModule { }
