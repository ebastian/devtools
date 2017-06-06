import { Entity } from '../../shared/entity/entity';

import { ProductComponent } from './product-component';

export class ComponentVersion extends Entity {
  
  major: string;
  minor: string;
  release: string;
  creation: Date;
  death: Date;
  component: ProductComponent;

  constructor(component: ProductComponent) { 
    super();
    this.component = component;
    this.death = null;
  };
}