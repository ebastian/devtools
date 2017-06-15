import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { ProductComponent } from '../../products-components/service/product-component';
import { ProductComponentService } from '../../products-components/service//product-component.service';

import { ComponentVersion } from '../../products-components/service/component-version';
import { ComponentVersionService } from '../../products-components/service//component-version.service';

import { BuildUpload } from '../service/build-upload';

@Component({
  selector: 'app-upload-build',
  templateUrl: './upload-build.component.html',
  styleUrls: ['./upload-build.component.css'],
  providers: [ 
    ProductComponentService,
    ComponentVersionService 
  ]
})
export class UploadBuildComponent implements OnInit {

  components: Array<ProductComponent>;
  selectedComponent:ProductComponent
  versions: Array<ComponentVersion>;
  selectedVersionId: number;

  buildsUpload: Array<any> = [];
  buildUpload: BuildUpload;

  constructor(protected componentService: ProductComponentService, protected versionService: ComponentVersionService) { 
    this.components = new Array<ProductComponent>();
    this.buildUpload = new BuildUpload(new ComponentVersion(new ProductComponent()));
    //seek by products with active versons and builds
    this.componentService.getItens().then(data => this.components = (data !== null ? data : new Array<ProductComponent>()));
  }

  ngOnInit() {
    console.clear();
  }

  componentSelect = (componentId: number) => {
    var component = this.components.find(c => c.id == componentId);
    this.buildUpload.version.component = component;
    if(component !== undefined && component.id !== undefined) {
      this.versionService.getActivesItensByComponentId(component.id).then(this.prepareResultVersions).then(this.sortVersions);
    }
  }

  versionSelect = (versionId: number) => {
    var version = this.versions.find(v => v.id == versionId);
    version.component = this.buildUpload.version.component;
    this.buildUpload.version = version;
  }

  fileSelect = (a) => {
    //this.buildUpload.file = File;
    console.log(JSON.stringify(a));
    console.log(JSON.stringify(this.buildUpload.file));
  }

  prepareResultVersions = data => this.versions = (data !== null ? data as ComponentVersion[] : new Array<ComponentVersion>());
  sortVersions = () => this.versions.sort((item1, item2) => item2.id - item1.id);

  clickSave = () => this.buildsUpload.push(this.buildUpload);
}
