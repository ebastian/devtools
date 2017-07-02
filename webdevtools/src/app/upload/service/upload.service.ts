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

  public getVersionBuilds(componentId: number, versionId: number): Promise<BuildUpload[]> {
    const url = this.baseUrl + "/" + componentId + "/version/" + versionId + "/build";
    return this.http.get(url, { headers: this.headers })
      .toPromise()
      .then(this.castResults)
      .catch(this.handleError);
  }

  public saveBuild(buildUpload: BuildUpload) {
    var url = this.baseUrl + "/" + buildUpload.version.component.id
      + "/version/" + buildUpload.version.id
      + "/build";

    var d = new Date();
    d.setHours(d.getHours() - 3);
    buildUpload.creation = d;

    var item = buildUpload.getBuildInfo();
    var data = JSON.stringify(item).replace(/Z\"/, "\"");

    if (item.id == null || item.id == undefined) {
      return this.http.post(url, data, { headers: this.headers })
        .toPromise()
        .then(this.castResult)
        .catch(this.handleError);

    } else {
      url += "/" + item.id
      return this.http.put(url, data, { headers: this.headers })
        .toPromise()
        .then(this.castResult)
        .catch(this.handleError);
    }
  }

  public uploadBuild(buildUpload: BuildUpload) {

    const url = this.baseUrl + "/" + buildUpload.version.component.id
      + "/version/" + buildUpload.version.id
      + "/build/" + buildUpload.id
      + "/upload";

    var headersUpload = new Headers({
      'user-token': this.authService.userToken,
      'session-token': this.authService.sessionToken
    });

    let formData: FormData = new FormData();
    formData.append('uploadedFile', buildUpload.file, buildUpload.file.name);

    return this.http.post(url, formData, { headers: headersUpload })
      .toPromise()
      .then(this.castUploadResult(buildUpload.id))
  }

  castUploadResult = (buildUploadId: number) => (result: any) => buildUploadId;

  public delete(buildUpload: BuildUpload): Promise<void> {
    const url = this.baseUrl + "/" + buildUpload.version.component.id
      + "/version/" + buildUpload.version.id
      + "/build/" + buildUpload.id;

    return this.http.delete(url, { headers: this.headers })
      .toPromise()
      .then((res) => JSON.stringify(res))
      .catch(this.handleError);
  }

  public getDownloadLink = (buildUpload: BuildUpload) => {
    return this.baseUrl + "/" + buildUpload.version.component.id
      + "/version/" + buildUpload.version.id
      + "/build/" + buildUpload.id
      + "/download";
  }

  public downloadBuild(buildUpload: BuildUpload) {

    const url = this.baseUrl + "/" + buildUpload.version.component.id
      + "/version/" + buildUpload.version.id
      + "/build/" + buildUpload.id
      + "/download" + "/" + buildUpload.version.component.fileName;

    var headersDownload = new Headers({
      'Accept': 'application/octet-stream',
      'user-token': this.authService.userToken,
      'session-token': this.authService.sessionToken
    });

    return this.http.get(url, { headers: headersDownload })
      .subscribe(data => this.downloadFile(data)),//console.log(data),
      error => console.log("Error downloading the file."),
      () => console.info("OK");
    /*      
    return this.http.get(url, { headers: headersDownload })
      .toPromise()
      .then(data => this.downloadFile(data))
      .catch(this.handleError);
    */
  }

  downloadFile(data: any) {
    //var blob = new Blob([data._body], {type: "application/zip"});
    var blob = new Blob([data._body], { type: "application/octet-stream" });
    var url = window.URL.createObjectURL(blob);
    window.open(url);
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