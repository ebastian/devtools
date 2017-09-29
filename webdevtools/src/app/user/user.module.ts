import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UserComponent } from './user.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserFormComponent } from './user-form/user-form.component';
import { UserPrivilegesTabComponent } from './user-privileges-tab/user-privileges-tab.component';
import { EbAppCore } from '../shared/eb-app-core.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    EbAppCore
  ],
  declarations: [
    UserComponent, 
    UserListComponent, 
    UserFormComponent, 
    UserPrivilegesTabComponent
  ]
})
export class UserModule { }
