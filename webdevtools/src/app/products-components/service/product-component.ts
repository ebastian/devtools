import { Entity } from '../../shared/entity/entity';

export class ProductComponent extends Entity {
  
  name: string;
  description: string;
  filename: string;
  creation: Date;
  death: Date;
  //components: ProductComponent[];

  constructor() { 
    super();
  };

  //public isActive = (): boolean => this.death === undefined || this.death === null; how to do it???
}