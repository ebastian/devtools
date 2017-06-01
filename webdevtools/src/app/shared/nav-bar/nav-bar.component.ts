import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'eb-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css'],
  providers: [
    AuthService
  ]
})
export class NavBarComponent implements OnInit {

  opened: boolean = false;

  @Output()
  onMenuToggle = new EventEmitter();

  constructor(public authService: AuthService) {  
  }

  ngOnInit() {
  }

  toggleMenu(event): void {
    this.opened = !this.opened;
    this.onMenuToggle.next(this.opened);
  }

  logout(): void {
    this.authService.logout();
  }

}
