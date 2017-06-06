import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { AlertModule } from 'ng2-bootstrap';

import { AuthService } from './shared/auth/auth.service';
import { AuthGuard } from './shared/auth/auth-guard.service';

import { EbAppCore } from './shared/eb-app-core.module';

import { APPCONFIG, AppConfig } from './app.config';
import { AppComponent } from './app.component';

import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

import { DashboardModule } from './dashboard/dashboard.module';
import { ProductsModule } from './products/products.module';
import { ProductsComponentsModule } from './products-components/products-components.module';
import { UploadModule } from './upload/upload.module';
import { DownloadModule } from './download/download.module';

import { routes } from './routes';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AlertModule.forRoot(),
    RouterModule.forRoot(routes),
    DashboardModule,
    ProductsModule,
    ProductsComponentsModule,
    UploadModule,
    DownloadModule,
    EbAppCore
  ],
  providers: [
    AuthService,
    AuthGuard,
    { provide: APPCONFIG, useValue: AppConfig }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }