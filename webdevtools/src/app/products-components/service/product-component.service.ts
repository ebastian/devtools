import { Injectable, Inject } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { APPCONFIG } from '../../app.config';
import { IAppConfig } from '../../iapp.config';

import { AuthService } from '../../shared/auth/auth.service';

import { ProductComponent } from './product-component';
import { PRODUCTS_COMPONENTS } from './products-components.mock';

@Injectable()
export class ProductComponentService {

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

    console.log(config);
  }

  public getItens(): Promise<ProductComponent[]> {
    console.log(">>> " + JSON.stringify(this.headers));
    const url = this.config.apiEndpoint + "component";
    return this.http.get(url, { headers: this.headers })
      .toPromise()
      .then(this.castResults)
      .catch(this.handleError);
  }

  public save(item: ProductComponent): Promise<ProductComponent> {
    this.setCreation(new Date())(item);
    
    //item.creation = '2017-05-28T23:47:01.278579';
    
    console.log('save ' + JSON.stringify(item));
    if(item.id != undefined && item.id !== null) {
      const url = this.config.apiEndpoint + "component/" + item.id;
      return this.http.put(url, JSON.stringify(item), { headers: this.headers })
        .toPromise()
        .then(this.castResult)
        .catch(this.handleError);
    } else {
      const url = this.config.apiEndpoint + "component";
      return this.http.post(url, JSON.stringify(item), { headers: this.headers })
        .toPromise()
        .then(this.castResult)
        .catch(this.handleError);
    }
  }

  public getItem(id: number): Promise<ProductComponent> {
    console.log('getItem ' + id);
    const url = this.config.apiEndpoint + "component/" + id;
    return this.http.get(url, { headers: this.headers })
      .toPromise()
      .then(response => response.json() as ProductComponent)
      .catch(this.handleError);
  }

  public remove(id: number): Promise<void> {
    const url = this.config.apiEndpoint + "component/" + id;
    console.log('delete ' + id);
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

  castResult = item => item.json() as ProductComponent;
  castResults = response => response.json().list as ProductComponent[];
  setCreation = (creationDate: Date) => (record: ProductComponent) => record.creation = (record.creation === undefined ? new Date() : (record.creation));

}