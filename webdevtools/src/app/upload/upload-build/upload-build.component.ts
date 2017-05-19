import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { ProductComponent } from '../../products-components/service/product-component';
import { ProductComponentService } from '../../products-components/service//product-component.service';

import { ComponentVersion } from '../../products-components/service/component-version';
import { ComponentVersionService } from '../../products-components/service//component-version.service';

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
  selectedComponentId:number
  versions: Array<ComponentVersion>;
  selectedVersionId: number;
  buildNumber: number = 1;
  buildsUpload: Array<any> = [];
  buildUpload: any = {
     "component": 0,
      "version": 0,
      "build": 0,
      "file": 'aaa'
  };
  buildFile: '';

  constructor(protected componentService: ProductComponentService, protected versionService: ComponentVersionService) { 
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

  prepareResultVersions = data => this.versions = (data !== null ? data as ComponentVersion[] : new Array<ComponentVersion>());
  sortVersions = () => this.versions.sort((item1, item2) => item2.id - item1.id);

  clickSave = () => this.buildsUpload.push(
    {
      "component": this.selectedComponentId,
      "version": 1,
      "build": this.buildNumber,
      "file": this.buildFile
    }
  );

  //clickRemove = (obj:[]) => this.buildsUpload.findIndex(obj).; 

}
