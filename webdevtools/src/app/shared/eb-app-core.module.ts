import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { LoginComponent } from './login/login.component';
import { DatePipe } from './pipes/date.pipe';

@NgModule({
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    MainMenuComponent, 
    NavBarComponent,
    LoginComponent,
    DatePipe
  ],
  declarations: [
    MainMenuComponent, 
    NavBarComponent,
    LoginComponent,
    DatePipe
  ]
})
export class EbAppCore { }
