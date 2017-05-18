import { Injectable } from '@angular/core';

import { GenericService } from '../../shared/service/generic.service';

import { Product } from './product';
import { PRODUCTS } from './products.mock';

@Injectable()
export class ProductService extends GenericService {

  id = "ProductService";

  data: Product[] = PRODUCTS;

  constructor() { 
    super();
  }

}