import { Entity } from '../../shared/entity/entity';

//import { ComponentVersion } from '../../products-components/service/component-version';

export class BuildUpload extends Entity {
  
  version: number;
  build: number;
  notes: string;
  creation: Date;
  complete: Date;
  death: Date;

  constructor(version: number) { 
    super();
    this.version = version;
    this.death = null;
  };
}