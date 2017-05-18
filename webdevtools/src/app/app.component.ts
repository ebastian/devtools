import { Component, ViewChild, OnInit, AfterContentInit, EventEmitter, Input, Output  }      from '@angular/core';

import { MainMenuComponent } from './shared/main-menu/main-menu.component'
import { NavBarComponent } from './shared/nav-bar/nav-bar.component' //eb-app-core.module ??

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterContentInit {

  title: string = 'app works!';
  menuVisible: boolean = true;

  @ViewChild('mainMenu')
  protected menu: MainMenuComponent;

  @ViewChild('navBar')
  protected navBar: NavBarComponent;
 
  constructor() {

  }

  ngOnInit(): void {
  }

  ngAfterContentInit(): void {
    this.renderMenu();
  }

  onMenuToggle() {
    this.menuVisible = !this.menuVisible;
    this.renderMenu();
  }

  renderMenu() {
    if(this.menuVisible) {
      document.getElementById("wrapper").style.paddingLeft = '250px';
      //document.getElementById("wrapper").style.marginRight = '250px';
      document.getElementById("sidebar-wrapper").style.width = '250px';
    } else {
      document.getElementById("wrapper").style.paddingLeft = '0px';
      //document.getElementById("wrapper").style.marginRight = '0px';
      document.getElementById("sidebar-wrapper").style.width = '0px';
    }
  }

}
