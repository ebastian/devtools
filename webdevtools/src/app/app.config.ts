import { OpaqueToken } from "@angular/core";

export let APPCONFIG = new OpaqueToken("app.config");

export interface IAppConfig {
    apiEndpoint: string;
}

export const AppConfig: IAppConfig = {    
    apiEndpoint: "http://localhost:8080/apidevtools/api/"    
};