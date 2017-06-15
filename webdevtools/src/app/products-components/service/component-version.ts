import { Entity } from '../../shared/entity/entity';

import { ProductComponent } from './product-component';

export class ComponentVersion extends Entity {
  
  major: number;
  minor: number;
  release: number;
  creation: Date;
  death: Date;
  component: ProductComponent;

  constructor(component: ProductComponent) { 
    super();
    this.component = component;
    this.death = null;
    this.major = 1;
    this.minor = 0;
    this.release = 0;
  };
}