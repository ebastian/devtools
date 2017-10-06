import { Component, OnInit } from '@angular/core';

import { ProductComponent } from '../products-components/service/product-component';
import { ProductComponentService } from '../products-components/service//product-component.service';

import { ComponentVersion } from '../products-components/service/component-version';
import { ComponentVersionService } from '../products-components/service//component-version.service';

import { BuildUpload } from '../upload/service/build-upload';
import { UploadService } from '../upload/service/upload.service';

@Component({
  selector: 'app-validate',
  templateUrl: './validate.component.html',
  styleUrls: ['./validate.component.css'],
  providers: [
    ProductComponentService,
    ComponentVersionService,
    UploadService
  ]
})
export class ValidateComponent implements OnInit {

  components: Array<ProductComponent>;
  selectedComponent: ProductComponent;
  versions: Array<ComponentVersion>;
  selectedVersion: ComponentVersion;

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
    this.selectedComponent = this.components.find(c => c.id == componentId);
    this.versionService.getActivesItensByComponentId(this.selectedComponent.id)
      .then(this.prepareResultVersions)
      .then(this.sortVersions)
      .then(this.selectLastVersion);
  }

  selectLastVersion = () => this.versions.length > 0 ? this.versionSelect(this.versions[0].id) : false;
  versionSelect = (versionId: number) => {
    this.selectedVersion = this.versions.find(v => v.id == versionId);
    this.selectedVersion.component = this.selectedComponent;
    this.uploadService.getVersionBuilds(this.selectedComponent.id, this.selectedVersion.id)
      .then(builds => this.builds = builds.filter(b => b.size != undefined))
      .then(() => this.builds.map(b => b.version = this.selectedVersion));
  }

  prepareResultVersions = data => this.versions = (data !== null ? data as ComponentVersion[] : new Array<ComponentVersion>());
  sortVersions = () => this.versions.sort((item1, item2) => item2.id - item1.id);

  clickDownload = (build: BuildUpload) => {
    //this.uploadService.downloadBuild(build);
    this.uploadService.getDownloadLink(build).then(url => window.open(url));
  }

  clickCopyLink = (build: BuildUpload) => {
    this.uploadService.getDownloadLink(build).then(url => {
      alert(//build.version.component.name + " - "
            //+ build.version.major + "." + build.version.minor + "." + build.version.release + "\n" +
            url);
    });
  }

  toggleVersionActive = (build:BuildUpload) => {
    if(build.id === 1) {
      //this.uploadService.kill(this.productComponent.id, version.id).then(this.toggleActiveResponse('kill'));
      alert('Esta build está reprovada. Deseja validar?');
    } else if(build.id === 2) {
      //this.uploadService.revive(this.productComponent.id, version.id).then(this.toggleActiveResponse('revive'));
      alert('Esta build está aprovada e publicada. Deseja invalidar?');
    } else {
      alert('Esta build está pendente. Deseja validar?');
    }
  };

  toggleActiveResponse = action => (success: boolean): void => {
      //this.loadItens();
  }
}