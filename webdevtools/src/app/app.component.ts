import { Component, ViewChild, OnInit, AfterContentInit, EventEmitter, Input, Output } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { AuthService } from './shared/auth/auth.service';

import { MainMenuComponent } from './shared/main-menu/main-menu.component'
import { NavBarComponent } from './shared/nav-bar/nav-bar.component' //eb-app-core.module ??

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit, AfterContentInit {

  title: string = 'DevTools!';
  menuOpened: boolean = true;

  subscription:Subscription;

  @ViewChild('mainMenu')
  protected menu: MainMenuComponent;

  @ViewChild('navBar')
  protected navBar: NavBarComponent;

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
    this.subscription = this.authService.onToggleLogIn.subscribe(loggedIn => {
      console.log("appcomp.authService.loggedIn: " + loggedIn);
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  ngAfterContentInit(): void {
  }

  onNavMenuToggle(menuOpened: boolean) {
    this.menuOpened = menuOpened;
    this.renderMenu();
  }

  onLogout() {
    this.authService.logout();
  }

  renderMenu() {
    if (this.menuOpened) {
      document.getElementById("wrapper").style.paddingLeft = '250px';
      document.getElementById("sidebar-wrapper").style.width = '250px';
    } else {
      document.getElementById("wrapper").style.paddingLeft = '0px';
      document.getElementById("sidebar-wrapper").style.width = '0px';
    }
  }

}
