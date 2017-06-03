import { Component, OnInit, AfterContentInit, Input } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { ProductComponent } from '../service/product-component';
import { ComponentVersion } from '../service/component-version';

import { ComponentVersionService } from '../service/component-version.service';

@Component({
  selector: 'app-products-components-versions',
  templateUrl: './products-components-versions.component.html',
  styleUrls: ['./products-components-versions.component.css'],
  providers: [ ComponentVersionService ]
})
export class ProductsComponentsVersionsComponent implements OnInit {

  @Input()
  productComponent: ProductComponent;

  versions: Array<ComponentVersion>;

  version: ComponentVersion;

  filterActives: boolean = true;

  constructor(protected service: ComponentVersionService, private route: ActivatedRoute, private router: Router) {
    this.versions = new Array<ComponentVersion>();
  }

  ngOnInit() {
  }

   ngAfterContentInit():void {
    this.version = new ComponentVersion(this.productComponent);
    this.loadItens();
  }

  clickSave = (version: ComponentVersion) => { 
    this.version.creation =new Date();
    this.service.save(version).then(() => {
      this.version = new ComponentVersion(this.productComponent);
      this.loadItens();
    });
  }

  clickClear = () => this.version = new ComponentVersion(this.productComponent);

  loadItens():void {
    if(this.filterActives) {
      this.service.getActivesItensByComponentId(this.productComponent.id).then(this.assignVersions);
    } else {
      this.service.getItens(this.productComponent.id).then(this.assignVersions);
    }
  }

  toggleFilterActives = () => this.loadItens();
  assignVersions = data => this.versions = (data !== null ? data as ComponentVersion[] : new Array<ComponentVersion>());
  toggleVersionActive = (version:ComponentVersion) => version.death = (version.death === undefined || version.death === null ? new Date() : null);
}
