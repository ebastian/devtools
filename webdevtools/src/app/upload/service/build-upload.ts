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

  getDownloadLink(hash: String, fileName: String = "") {
    console.log(this.build);
    console.log(this.version.creation);
    console.log(this.version.component.fileName);
     return this.version.component.id
      + "/version/" + this.version.id
      + "/build/" + this.id + "/hash/download/"
      + hash + "/" 
      + (fileName !== undefined ? fileName : this.version.component.fileName);
  }

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