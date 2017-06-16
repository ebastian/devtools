import { Injectable, Inject } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { APPCONFIG } from '../../app.config';
import { IAppConfig } from '../../iapp.config';

import { AuthService } from '../../shared/auth/auth.service';
import { BuildUpload } from './build-upload';

@Injectable()
export class UploadService {

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

  public getItens(componentId: number, versionId: number): Promise<BuildUpload[]> {
    const url = this.baseUrl + "/" + componentId + "/version/" + versionId + "/build";
    console.log(url);
    return this.http.get(url, { headers: this.headers })
      .toPromise()
      .then(this.castResults)
      .catch(this.handleError);
  }

  public saveBuild(buildUpload: BuildUpload) {
    const url = this.baseUrl + "/" + buildUpload.version.component.id
      + "/version/" + buildUpload.version.id
      + "/build";

    return this.http.post(url, JSON.stringify({
      versionId: buildUpload.version.id,
      build: buildUpload.build,
      notes: buildUpload.notes,
      creation: buildUpload.creation,
      file: buildUpload.file,
    }), { headers: this.headers })
      .toPromise()
      .catch(this.handleError);
  }

  public uploadBuild(buildUpload: BuildUpload) {

    const url = this.baseUrl + "/" + buildUpload.version.component.id
      + "/version/" + buildUpload.version.id
      + "/build/" + buildUpload.id
      + "/upload";

    console.log(url);

    var headersUpload = new Headers({
      'Content-Type': 'multipart/form-data',
      'Accept': 'application/json',
      'user-token': this.authService.userToken,
      'session-token': this.authService.sessionToken
    });

    let formData: FormData = new FormData();
    formData.append('uploadFile', buildUpload.file, buildUpload.file.name);

    return this.http.post(url, formData, { headers: headersUpload })
      .toPromise()
      .catch(this.handleError);

    /*
      let headers = new Headers();
      headers.append('Content-Type', 'multipart/form-data');
      headers.append('Accept', 'application/json');
      let options = new RequestOptions({ headers: headers });
      this.http.post(url, formData, options)
        .map(res => res.json())
        .catch(error => Observable.throw(error))
        .subscribe(
        data => console.log('success'),
        error => console.log(error)
        )
    */
  }

  /*
  public save(item: BuildUpload): Promise<BuildUpload> {
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
  */

  /*
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
  */

  private handleError(error: any): Promise<any> {
    alert('Erro: ' + JSON.stringify(error));
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  castResult = item => item.json() as BuildUpload;
  castResults = response => response.json().list as BuildUpload[];

}