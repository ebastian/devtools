import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  templateUrl: './login.component.html'
})

export class LoginComponent {
  
  loginInfo = {
    userName: 'admin',
    password: 'admin'
  }
  
  constructor(public authService: AuthService, public router: Router) { }
  
  login = () => {
    this.authService.login(this.loginInfo.userName, this.loginInfo.password).then(() => {
      if (this.authService.isLoggedIn()) {
        // Get the redirect URL from our auth service
        // If no redirect has been set, use the default
        let redirect = this.authService.redirectUrl ? this.authService.redirectUrl : '/dashboard';
        // Redirect the user
        this.router.navigate([redirect]);
      }
    });
    /*
    this.authService.login(this.loginInfo.userName, this.loginInfo.password).subscribe(() => {
      if (this.authService.isLoggedIn()) {
        // Get the redirect URL from our auth service
        // If no redirect has been set, use the default
        let redirect = this.authService.redirectUrl ? this.authService.redirectUrl : '/dashboard';
        // Redirect the user
        this.router.navigate([redirect]);
      }
    });
    */
  }
  
  logout = () => this.authService.logout;
}