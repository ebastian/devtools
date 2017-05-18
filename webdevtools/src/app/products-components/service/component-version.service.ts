import { Injectable } from '@angular/core';

import { GenericService } from '../../shared/service/generic.service';

import { ComponentVersion } from '../service/component-version';
import { COMPONENTS_VERSIONS } from '../service/components-versions.mock';

@Injectable()
export class ComponentVersionService extends GenericService {

  id = "ComponentVersionService";

  data: ComponentVersion[] = COMPONENTS_VERSIONS;

  constructor() { 
    super();
  }

  applyFilter = filter => itens => itens.filter(filter);
  
  public getActiveItens = (): Promise<ComponentVersion[]> => super.getItens().then(this.applyFilter(this.filterActives));
  filterActives = (item: ComponentVersion) => item.death === null;
  
  public getItensByComponentId = (componentId: number): Promise<ComponentVersion[]> => super.getItens().then(this.applyFilter(this.filterByComponentId(componentId)));
  filterByComponentId = componentId => (item: ComponentVersion) => item.component !== null && item.component.id == componentId;
  
  public getActivesItensByComponentId2 = (componentId: number): Promise<ComponentVersion[]> => this.getItensByComponentId(componentId).then(this.applyFilter(this.filterActives))

  public getActivesItensByComponentId(componentId: number): Promise<ComponentVersion[]> {
    return this.getActivesItensByComponentId2(componentId).then(comp => { console.log(comp.length); return Promise.all(comp); });
  }
}