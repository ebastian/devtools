import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { AlertModule } from 'ng2-bootstrap';

import { EbAppCore } from './shared/eb-app-core.module';

import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

import { DashboardModule } from './dashboard/dashboard.module';
import { ProductsModule } from './products/products.module';
import { ProductsComponentsModule } from './products-components/products-components.module';
import { UploadModule } from './upload/upload.module';

import { routes } from './app.routes';

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
    EbAppCore
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
