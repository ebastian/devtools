import { Routes } from '@angular/router';

import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { DashboardComponent } from './dashboard/dashboard.component';

import { ProductsComponent } from './products/products.component';
import { ProductsListComponent } from './products/products-list/products-list.component';
import { ProductFormComponent } from './products/product-form/product-form.component';

import { ProductsComponentsComponent } from './products-components/products-components.component';
import { ProductsComponentsListComponent } from './products-components/products-components-list/products-components-list.component';
import { ProductsComponentsFormComponent } from './products-components/products-components-form/products-components-form.component';

import { UploadComponent } from './upload/upload.component';
import { UploadBuildComponent } from './upload/upload-build/upload-build.component';
import { UploadDocumentComponent } from './upload/upload-document/upload-document.component';
import { UploadAppComponent } from './upload/upload-app/upload-app.component';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'produtos', component: ProductsComponent,
    children: [
      { path: '', component: ProductsListComponent },
      { path: 'produto', redirectTo: 'produto/', pathMatch: 'full' },
      { path: 'produto/:id', component: ProductFormComponent }
    ]
  },
  { path: 'componentes', component: ProductsComponentsComponent,
    children: [
          { path: '', component: ProductsComponentsListComponent },
          { path: ':id', component: ProductsComponentsListComponent },
          { path: 'componente', redirectTo: 'componente/', pathMatch: 'full' },
          { path: 'componente/:id', component: ProductsComponentsFormComponent },
          { path: 'componente/incluir', component: ProductsComponentsFormComponent }
    ]
  },
  { path: 'upload', component: UploadComponent,
    children: [
      { path: '', redirectTo: 'build', pathMatch: 'full' },
      { path: 'build', component: UploadBuildComponent },
      { path: 'documento', component: UploadDocumentComponent },
      { path: 'aplicativo', component: UploadAppComponent }
    ]
  },
  { path: '**', component: PageNotFoundComponent }
];