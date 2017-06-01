import { OpaqueToken } from "@angular/core";
import { IAppConfig } from './iapp.config';

export let APPCONFIG = new OpaqueToken("app.config");

export const AppConfig: IAppConfig = {    
    apiEndpoint: "http://localhost:8080/apidevtools/api/"    
};