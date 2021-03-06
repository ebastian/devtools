import { Route } from '@angular/router';

import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { DashboardComponent } from './dashboard/dashboard.component';

import { AuthGuard } from './shared/auth/auth-guard.service';
import { LoginComponent } from './shared/login/login.component';

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

import { DownloadComponent } from './download/download.component';

import { ValidateComponent } from './validate/validate.component';

import { UserComponent } from './user/user.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { UserFormComponent } from './user/user-form/user-form.component';

export const routes: Route[] = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'login', component: LoginComponent},
  { path: 'dashboard', component: DashboardComponent, canActivate: [ AuthGuard ]},
  { path: 'produtos', component: ProductsComponent, canActivate: [ AuthGuard ],
    children: [
      {
        path: '',
        canActivateChild: [ AuthGuard ],
        children: [
          { path: '', component: ProductsListComponent},
          { path: 'produto', redirectTo: 'produto/', pathMatch: 'full'},
          { path: 'produto/:id', component: ProductFormComponent}     
        ]
      }
    ]
  },
  { path: 'componentes', component: ProductsComponentsComponent, canActivate: [ AuthGuard ],
    children: [
      {
        path: '',
        canActivateChild: [ AuthGuard ],
        children: [
          { path: '', component: ProductsComponentsListComponent},
          { path: ':id', component: ProductsComponentsListComponent},
          { path: 'componente', redirectTo: 'componente/', pathMatch: 'full'},
          { path: 'componente/:id', component: ProductsComponentsFormComponent},
          { path: 'componente/incluir', component: ProductsComponentsFormComponent}
        ]
      }
    ]
  },
  { path: 'download', component: DownloadComponent, canActivate: [ AuthGuard ]}, 
  { path: 'validar', component: ValidateComponent, canActivate: [ AuthGuard ]}, 
  { path: 'usuarios', component: UserComponent, canActivate: [ AuthGuard ],
    children: [
      {
        path: '',
        canActivateChild: [ AuthGuard ],
        children: [
          { path: '', component: UserListComponent},
          { path: ':id', component: UserListComponent},
          { path: 'usuario', redirectTo: 'usuario/', pathMatch: 'full'},
          { path: 'usuario/:id', component: UserFormComponent},
          { path: 'usuario/incluir', component: UserFormComponent}
        ]
      }
    ]
  },
  { path: 'upload', component: UploadComponent, canActivate: [ AuthGuard ], 
    children: [
      {
        path: '',
        canActivateChild: [ AuthGuard ],
        children: [
          { path: '', redirectTo: 'build', pathMatch: 'full'},
          { path: 'build', component: UploadBuildComponent},
          { path: 'documento', component: UploadDocumentComponent},
          { path: 'aplicativo', component: UploadAppComponent}
        ]
      }
    ]
  },
  { path: '**', component: PageNotFoundComponent }
];