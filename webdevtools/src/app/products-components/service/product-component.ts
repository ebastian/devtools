import { Entity } from '../../shared/entity/entity';

export class ProductComponent extends Entity {
  
  name: string;
  description: string;
  creation: string;
  death: string;
  //components: ProductComponent[];

  constructor() { 
    super();
    /*
    this.name = "";
    this.description = "";
    this.creation = "";
    this.death = "";
    */
  };
}