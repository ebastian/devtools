import { Injectable, Inject } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { APPCONFIG } from '../../app.config';
import { IAppConfig } from '../../iapp.config';

import { AuthService } from '../../shared/auth/auth.service';

import { ComponentVersion } from '../service/component-version';

@Injectable()
export class ComponentVersionService {

  baseUrl: string;
  headers: Headers;

  constructor(@Inject(APPCONFIG) private config: IAppConfig, 
                private http: Http,
                private authService: AuthService) {

    this.headers = new Headers({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'user-token': authService.userToken,
      'session-token': authService.sessionToken
    });

    this.baseUrl = config.apiEndpoint + "component";
  }
  
  public getItens(componentId: number): Promise<ComponentVersion[]> {
    const url = this.baseUrl + "/" + componentId + "/version";
    return this.http.get(url, { headers: this.headers })
      .toPromise()
      .then(this.castResults)
      .catch(this.handleError);
  }

  public save(item: ComponentVersion): Promise<ComponentVersion> {
    const url = this.baseUrl + "/" + item.component.id + "/version";
    this.setCreation(new Date())(item);
    if(item.id != undefined && item.id !== null) {
      return this.http.put(url + "/" + item.id, JSON.stringify(item), { headers: this.headers })
        .toPromise()
        .then(this.castResult)
        .catch(this.handleError);
    } else {
      return this.http.post(url, JSON.stringify(item), { headers: this.headers })
        .toPromise()
        .then(this.castResult)
        .catch(this.handleError);
    }
  }

  public getItem(componentId:number, id: number): Promise<ComponentVersion> {
    const url = this.baseUrl + "/" + componentId + "/version/" + id;
    return this.http.get(url, { headers: this.headers })
      .toPromise()
      .then(response => response.json() as ComponentVersion)
      .catch(this.handleError);
  }

  public remove(componentId:number, id: number): Promise<void> {
    const url = this.baseUrl + "/" + componentId + "/version/" + id;
    return this.http.delete(url, { headers: this.headers })
      .toPromise()
      .then((res) => JSON.stringify(res))
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    alert('Erro: ' + JSON.stringify(error));
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  applyFilter = filter => itens => itens.filter(filter);

  public getActiveItens = (componentId: number): Promise<ComponentVersion[]> => this.getItens(componentId).then(this.applyFilter(this.filterActives));
  filterActives = (item: ComponentVersion) => item.death === null;
  
  public getActivesItensByComponentId(componentId: number): Promise<ComponentVersion[]> {
    return this.getActiveItens(componentId).then(comp => Promise.all(comp));
  }

  castResult = item => item.json() as ComponentVersion;
  castResults = response => response.json().list as ComponentVersion[];
  setCreation = (creationDate: Date) => (record: ComponentVersion) => record.creation = (record.creation === undefined ? new Date() : (record.creation));
}