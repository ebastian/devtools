import { Entity } from '../../shared/entity/entity';

import { ProductComponent } from './product-component';

export class ComponentVersion extends Entity {
  
  major: string;
  minor: string;
  release: string;
  creation: string;
  death: string;
  component: ProductComponent;

  constructor(component: ProductComponent) { 
    super();
    this.component = component;
    this.death = null;
    /*
    this.name = "";
    this.description = "";
    this.creation = "";
    this.death = "";
    */
  };
}