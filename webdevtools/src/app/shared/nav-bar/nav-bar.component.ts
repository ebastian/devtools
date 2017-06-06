import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'eb-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {
  
  opened: boolean = false;
  loggedIn: boolean = false;

  @Output()
  onMenuToggle = new EventEmitter();

  @Output()
  onLogout = new EventEmitter();

  subscription:Subscription;

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
    this.subscription = this.authService.onToggleLogIn.subscribe(loggedIn => {
      this.loggedIn = loggedIn;
      this.opened = loggedIn;
      this.onMenuToggle.next(this.opened);
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  toggleMenu(event): void {
    this.opened = !this.opened;
    this.onMenuToggle.next(this.opened);
  }

  logout(): void {
    this.opened = false;
    this.onMenuToggle.next(this.opened);
    this.onLogout.next();
  }

}
