import { Injectable, Inject } from '@angular/core';
import { Headers, Http } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/delay';
import 'rxjs/add/operator/toPromise';

import { APPCONFIG } from '../../app.config';
import { IAppConfig } from '../../iapp.config';

@Injectable()
export class AuthService {

  private headers = new Headers({
    'user-token': 'YWRtaW47YWRtaW4'
  });

  authUrl: string = this.config.apiEndpoint + "acess/login";

  isLoggedIn: boolean = false;

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  constructor( @Inject(APPCONFIG) private config: IAppConfig, private http: Http) {
    console.log(config);
  }

  login(): Observable<boolean> {
    //return Observable.of(true).delay(1000).do(val => this.isLoggedIn = true);
     return this.http.post(this.authUrl, null, { headers: this.headers })
                      .map(res => res.json())
  }

  logout(): void {
    this.isLoggedIn = false;
  }

  private extractData(res: any) {
    console.log('>>>>>>>>>>>>>>' + res);
    let body = res.json();
    return body.data || {};
  }

  private handleError(error: Response | any) {
    // In a real world app, we might use a remote logging infrastructure
    let errMsg: string;
    /*
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    */
    console.error(errMsg);
    return Promise.reject(errMsg);
  }
}