import { Entity } from '../../shared/entity/entity';

import { ProductComponent } from '../../products-components/service/product-component';

export class Product extends Entity {
  
  name: string;
  description: string;
  creation: string;
  death: string;
  components: Array<ProductComponent>;

  constructor() { 
    super();
    this.components = new Array<ProductComponent>();
    /*
    this.name = "";
    this.description = "";
    this.creation = "";
    this.death = "";
    */
  };
}