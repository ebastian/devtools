import { Entity } from '../../shared/entity/entity';

export class ProductComponent extends Entity {
  
  name: string;
  description: string;
  creation: Date;
  death: Date;
  //components: ProductComponent[];

  constructor() { 
    super();
  };
}