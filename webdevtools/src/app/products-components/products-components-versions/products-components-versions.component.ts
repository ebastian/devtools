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
    console.log(JSON.stringify(this.productComponent));
  }

   ngAfterContentInit():void {
    console.log(">> " + JSON.stringify(this.productComponent));
    this.version = new ComponentVersion(this.productComponent);
    this.loadItens();
  }

  clickSave = (version: ComponentVersion) => { 
    this.version.creation = this.formatDate(new Date());
    this.service.save(version);
    this.version = new ComponentVersion(this.productComponent);
    this.loadItens();
  }

  clickClear = () => this.version = new ComponentVersion(this.productComponent);

  loadItens():void {
    console.log(">> " + this.filterActives);
    if(this.filterActives) {
      this.service.getActivesItensByComponentId(this.productComponent.id).then(this.assignVersions);
    } else {
      this.service.getItensByComponentId(this.productComponent.id).then(this.assignVersions);
    }
  }

  formatDate = (d:Date):string => ("0" + d.getDate()).slice(-2) + "/" + ("0"+(d.getMonth()+1)).slice(-2) + "/" +d.getFullYear();

  toggleFilterActives = () => this.loadItens();
  
  assignVersions = data => this.versions = (data !== null ? data as ComponentVersion[] : new Array<ComponentVersion>());

  toggleVersionActive = (version:ComponentVersion) => version.death = (version.death === undefined || version.death === null ? this.formatDate(new Date()) : null);
}
