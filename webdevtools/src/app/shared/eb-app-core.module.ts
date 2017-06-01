import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    MainMenuComponent, 
    NavBarComponent,
    LoginComponent
  ],
  declarations: [
    MainMenuComponent, 
    NavBarComponent,
    LoginComponent
  ]
})
export class EbAppCore { }
