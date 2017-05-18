import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    MainMenuComponent, 
    NavBarComponent
  ],
  declarations: [
    MainMenuComponent, 
    NavBarComponent
  ]
})
export class EbAppCore { }
