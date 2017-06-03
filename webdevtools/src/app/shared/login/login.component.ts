import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  templateUrl: './login.component.html'
})

export class LoginComponent {
  
  constructor(public authService: AuthService, public router: Router) { }
  
  login = () => {
    this.authService.login().subscribe(() => {
      if (this.authService.isLoggedIn()) {
        // Get the redirect URL from our auth service
        // If no redirect has been set, use the default
        let redirect = this.authService.redirectUrl ? this.authService.redirectUrl : '/dashboard';
        // Redirect the user
        this.router.navigate([redirect]);
      }
    });
  }
  
  logout = () => this.authService.logout;
}