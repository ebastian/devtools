import { Entity } from '../../shared/entity/entity';

import { ComponentVersion } from '../../products-components/service/component-version';

export class BuildUpload extends Entity {
  
  version: ComponentVersion;
  build: number;
  notes: string;
  creation: Date;
  complete: Date;
  death: Date;
  file: File;

  constructor(version: ComponentVersion) { 
    super();
    this.version = version;
    this.death = null;
  };
}