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
  userToken: string = 'YWRtaW47YWRtaW4';
  sessionToken: string;

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  constructor( @Inject(APPCONFIG) private config: IAppConfig, private http: Http) {
    console.log(config);
  }

  login(): Observable<boolean> {
     window.localStorage.setItem('user-token', this.userToken);
    return this.http.post(this.authUrl, null, { headers: this.headers })
      .map(res => {
        this.sessionToken = res.json().hash;
        window.localStorage.setItem('session-token', this.sessionToken)
        this.isLoggedIn = true;
        return true
      })
  }

  logout(): void {
    window.localStorage.remove('user-token');
    window.localStorage.remove('session-token');
    this.isLoggedIn = false;
  }
    
}