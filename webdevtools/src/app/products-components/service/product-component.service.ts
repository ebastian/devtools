import { Injectable, Inject } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { APPCONFIG } from '../../app.config';
import { IAppConfig } from '../../iapp.config';

import { AuthService } from '../../shared/auth/auth.service';

import { ProductComponent } from './product-component';

@Injectable()
export class ProductComponentService {

  baseUrl: string;
  headers: Headers;

  constructor( @Inject(APPCONFIG) private config: IAppConfig,
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

  public getItens(): Promise<ProductComponent[]> {
    return this.http.get(this.baseUrl, { headers: this.headers })
      .toPromise()
      .then(this.castResults)
      .catch(this.handleError);
  }

  public save(item: ProductComponent): Promise<ProductComponent> {
    this.setCreation(new Date())(item);
    const url = this.baseUrl;
    if (item.id != undefined && item.id !== null) {
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

  public getItem(id: number): Promise<ProductComponent> {
    const url = this.baseUrl + "/" + id;
    return this.http.get(url, { headers: this.headers })
      .toPromise()
      .then(response => response.json() as ProductComponent)
      .catch(this.handleError);
  }

  public remove(id: number): Promise<void> {
    const url = this.baseUrl + "/" + id;
    return this.http.delete(url, { headers: this.headers })
      .toPromise()
      .then((res) => JSON.stringify(res))
      .catch(this.handleError);
  }

  public kill(item: ProductComponent): Promise<boolean> {
    const url = this.baseUrl + "/" + item.id + "/kill";
    return this.http.put(url, null, { headers: this.headers })
      .toPromise()
      .then(this.castResult)
      .catch(this.handleError);
  }

  public revive(item: ProductComponent): Promise<boolean> {
    const url = this.baseUrl + "/" + item.id + "/revive";
    return this.http.put(url, null, { headers: this.headers })
      .toPromise()
      .then(this.castResult)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    alert('Erro: ' + JSON.stringify(error));
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  castResult = item => item.json() as ProductComponent;
  castResults = response => response.json().list as ProductComponent[];
  setCreation = (creationDate: Date) => (record: ProductComponent) => record.creation = (record.creation === undefined ? new Date() : (record.creation));

}