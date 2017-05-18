import { Injectable } from '@angular/core';

import { GenericService } from '../../shared/service/generic.service';

import { ProductComponent } from './product-component';
import { PRODUCTS_COMPONENTS } from './products-components.mock'; 

@Injectable()
export class ProductComponentService extends GenericService {

  id = "ProductComponentService";

  data: ProductComponent[] = PRODUCTS_COMPONENTS;

  constructor() { 
    super();
  }

}