import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductsComponentsComponent } from './products-components.component';
import { ProductsComponentsListComponent } from './products-components-list/products-components-list.component';
import { ProductsComponentsFormComponent } from './products-components-form/products-components-form.component';
import { ProductsComponentsTabComponent } from './products-components-tab/products-components-tab.component';
import { ProductsComponentsVersionsComponent } from './products-components-versions/products-components-versions.component';
import { ComponentVersion } from './service/component-version';
import { ProductComponent } from './service/product-component';
import { EbAppCore } from '../shared/eb-app-core.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    EbAppCore
  ],
  declarations: [
    ProductsComponentsComponent, 
    ProductsComponentsListComponent, 
    ProductsComponentsFormComponent, 
    ProductsComponentsTabComponent, 
    ProductsComponentsVersionsComponent
  ],
  exports: [ 
    ProductsComponentsComponent
  ]
})
export class ProductsComponentsModule { }
