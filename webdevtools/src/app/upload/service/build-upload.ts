import { Entity } from '../../shared/entity/entity';

import { ComponentVersion } from '../../products-components/service/component-version';

export class BuildUpload extends Entity {
  
  version: ComponentVersion;
  build: number;
  creation: Date;
  complete: Date;
  death: Date;
  notes: string;
  size: number;
  file: File;
  status: number;

  getBuildInfo() {
    var a = {
      id: this.id,
      build: this.build,
      creation: this.creation,
      complete: this.complete,
      death: this.death,
      size: this.size,
      notes: this.notes
    }; 
    return a;
  }

  constructor(version: ComponentVersion) { 
    super();
    this.version = version;
    this.death = null;
    this.status = 0;
  };
}