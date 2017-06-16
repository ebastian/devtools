import { Component, OnInit } from '@angular/core';

import { ProductComponent } from '../products-components/service/product-component';
import { ProductComponentService } from '../products-components/service//product-component.service';

import { ComponentVersion } from '../products-components/service/component-version';
import { ComponentVersionService } from '../products-components/service//component-version.service';

import { BuildUpload } from '../upload/service/build-upload';
import { UploadService } from '../upload/service/upload.service';

@Component({
  selector: 'app-download',
  templateUrl: './download.component.html',
  styleUrls: ['./download.component.css'],
   providers: [ 
    ProductComponentService,
    ComponentVersionService,
    UploadService 
  ]
})
export class DownloadComponent implements OnInit {

  components: Array<ProductComponent>;
  selectedComponentId:number
  versions: Array<ComponentVersion>;
  selectedVersionId: number;

  builds: Array<BuildUpload>;

  constructor(protected componentService: ProductComponentService, 
              protected versionService: ComponentVersionService,
              protected uploadService: UploadService) { 
    this.components = new Array<ProductComponent>();
    //seek by products with active versons and builds
    this.componentService.getItens().then(data => this.components = (data !== null ? data as ProductComponent[] : new Array<ProductComponent>()));
  }

  ngOnInit() {
  }

  componentSelect = (componentId: number) => {
    this.selectedComponentId = componentId;
    this.versionService.getActivesItensByComponentId(this.selectedComponentId).then(this.prepareResultVersions).then(this.sortVersions);
  }

  versionSelect = (versionId: number) => {
    this.selectedVersionId = versionId;
    console.log(this.selectedComponentId, this.selectedVersionId);
    this.uploadService.getVersionBuilds(this.selectedComponentId, this.selectedVersionId).then(
      builds => this.builds = builds
    );
  }

  prepareResultVersions = data => this.versions = (data !== null ? data as ComponentVersion[] : new Array<ComponentVersion>());
  sortVersions = () => this.versions.sort((item1, item2) => item2.id - item1.id);

}
