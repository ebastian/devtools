import { Injectable, Inject } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { APPCONFIG } from '../../app.config';
import { IAppConfig } from '../../iapp.config';

import { AuthService } from '../../shared/auth/auth.service';

import { User } from './user';

@Injectable()
export class UserService {

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

    this.baseUrl = config.apiEndpoint + "user";
  }

  public getItens(): Promise<User[]> {
    const url = this.baseUrl;
    return this.http.get(url, { headers: this.headers })
      .toPromise()
      .then(this.castResults)
      .catch(this.handleError);
  }

  public save(user: User): Promise<User> {
    const url = this.baseUrl;
    user.type = 'ADMIN';
    user.password = undefined;
    if (user.id != undefined && user.id !== null) {
      return this.http.put(url + "/" + user.id, JSON.stringify(user), { headers: this.headers })
        .toPromise()
        .then(this.castResult)
        .catch(this.handleError);
    } else {
      var d = new Date();
      d.setHours(d.getHours()-3);
      user.creation = d;
      var data = JSON.stringify(user).replace(/Z\"/, "\"");
      return this.http.post(url, data, { headers: this.headers })
        .toPromise()
        .then(this.castResult)
        .catch(this.handleError);
    }
  }

  public getUser(id: number): Promise<User> {
    const url = this.baseUrl + "/" + id;
    return this.http.get(url, { headers: this.headers })
      .toPromise()
      .then(response => response.json() as User)
      .catch(this.handleError);
  }

  public remove(id: number): Promise<void> {
    const url = this.baseUrl + "/" + id;
    return this.http.delete(url, { headers: this.headers })
      .toPromise()
      .then((res) => JSON.stringify(res))
      .catch(this.handleError);
  }

  public kill(id: number): Promise<boolean> {
    const url = this.baseUrl + "/" + id + "/kill";
    return this.http.put(url, null, { headers: this.headers })
      .toPromise()
      .then(this.castResult)
      .catch(this.handleError);
  }

  public revive(id: number): Promise<boolean> {
    const url = this.baseUrl + "/" + id + "/revive";
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

  applyFilter = filter => itens => itens.filter(filter);

  public getActiveItens = (): Promise<User[]> => this.getItens().then(this.applyFilter(this.filterActives));
  filterActives = (user: User) => user.death === null;

  castResult = user => user.json() as User;
  castResults = response => response.json().list as User[];
}