import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { ProductComponent } from '../../products-components/service/product-component';
import { ProductComponentService } from '../../products-components/service//product-component.service';

import { ComponentVersion } from '../../products-components/service/component-version';
import { ComponentVersionService } from '../../products-components/service//component-version.service';

import { UploadService } from '../service/upload.service';
import { BuildUpload } from '../service/build-upload';

import { AuthService } from '../../shared/auth/auth.service';

@Component({
  selector: 'app-upload-build',
  templateUrl: './upload-build.component.html',
  styleUrls: ['./upload-build.component.css'],
  providers: [
    ProductComponentService,
    ComponentVersionService,
    UploadService,
    AuthService
  ]
})
export class UploadBuildComponent implements OnInit {

  components: Array<ProductComponent>;
  selectedComponent: ProductComponent
  versions: Array<ComponentVersion>;
  selectedVersionId: number;
  builds: Array<BuildUpload>;

  buildsUpload: Array<BuildUpload>;
  buildUpload: BuildUpload;

  constructor(protected componentService: ProductComponentService,
    protected versionService: ComponentVersionService,
    protected uploadService: UploadService,
    protected authService: AuthService) {

    this.components = new Array<ProductComponent>();
    this.versions = new Array<ComponentVersion>();
    this.buildsUpload = new Array<BuildUpload>();

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
    if (component !== undefined && component.id !== undefined) {
      this.versionService.getActivesItensByComponentId(component.id)
        .then(this.prepareResultVersions)
        .then(this.sortVersions)
        .then(this.selectLastVersion);
    }
  }

  selectLastVersion = () => this.versions.length > 0 ? this.versionSelect(this.versions[0].id) : false;
  versionSelect = (versionId: number) => {
    var version = this.versions.find(v => v.id == versionId);
    if (version != undefined) {
      version.component = this.buildUpload.version.component;
      this.buildUpload.version = version;
      this.getBuilds();
    }
  }

  getBuilds = () => {
    this.uploadService.getVersionBuilds(this.buildUpload.version.component.id, this.buildUpload.version.id)
      .then(this.sortBuilds)
      .then(this.prepareResultBuilds)
      .then(this.selectLastBuild);
  }

  selectLastBuild = () => this.builds ? this.buildSelect(this.builds[0].build) : false;
  buildSelect = (buildNumber: any) => {
    var buildUpload = this.builds.find(b => b.build == buildNumber);
    if (buildUpload != undefined) {
      this.buildUpload.id = buildUpload.id;
      this.buildUpload.build = buildUpload.build;
      this.buildUpload.notes = buildUpload.notes;
    }
  }

  fileSelect = ($event) => {
    let fileList: FileList = $event.target.files || $event.srcElement.files;
    if (fileList.length > 0) {
      this.buildUpload.file = fileList[0];
    }
  }

  prepareResultVersions = data => this.versions = (data !== null ? data as ComponentVersion[] : new Array<ComponentVersion>());
  sortVersions = versions => versions.sort((item1, item2) => item2.id - item1.id);

  prepareResultBuilds = data => {
    var novaBuild = new BuildUpload(this.buildUpload.version);
    if (data !== null && data.length > 0) {
      data = data as Array<BuildUpload>;
      novaBuild.build = data[0].build + 1;
    } else {
      data = new Array<BuildUpload>();
      novaBuild.build = 1;
    }
    data.unshift(novaBuild);
    this.builds = data;
  };
  sortBuilds = builds => builds.sort((item1, item2) => item2.id - item1.id);

  sendBuild = () => {
    if(this.buildUpload.file == undefined) {
      alert("É necessário selecionar um arquivo!");
      return;
    }
    this.uploadService.saveBuild(this.buildUpload).then(buildUpload => {
      this.buildUpload.id = buildUpload.id;
      this.buildUpload.creation = buildUpload.creation;
      this.buildsUpload.push(this.buildUpload);
      this.uploadFile(this.buildUpload);
    });
  }

  uploadFile = (buildUpload: BuildUpload) => {
    buildUpload.status = 0;
    this.uploadService.uploadBuild(buildUpload)
      .then(this.uploadBuildResult)
      .catch(this.uploadBuildFault(buildUpload.id));
  }

  uploadBuildResult = (buildUploadId: number) => {
    this.buildsUpload.find(item => item.id == buildUploadId).status = 1;
    this.buildUpload = new BuildUpload(this.buildUpload.version);
    document.getElementById("exampleInputFile").nodeValue = null;
    this.getBuilds();
  }

  uploadBuildFault = (buildUploadId: number) => (result: any) => {
    this.buildsUpload.find(item => item.id == buildUploadId).status = 2;
    this.buildUpload = new BuildUpload(this.buildUpload.version);
    document.getElementById("exampleInputFile").nodeValue = null;
    this.getBuilds();
  }

  clickRepeat = (b: BuildUpload) => {
    this.uploadFile(b);
  }

  clickDelete = (b: BuildUpload) => {
    this.uploadService.delete(b).catch(a => alert(a));
  }
}
