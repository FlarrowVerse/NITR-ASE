import { Component } from '@angular/core';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { NavbarComponent } from "../../shared/components/navbar/navbar.component";
import { AuthService } from '../../core/services/auth.service';
import { LoginRequest } from '../../shared/models/login-request.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NavbarComponent, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginData = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  constructor(private authService: AuthService, private router: Router) {}

  handleLogin(): void {
    const loginRequest: LoginRequest = {
      username: this.loginData.value.username || '',
      password: this.loginData.value.password || ''
    };

    this.authService.login(loginRequest).subscribe(
      (message: string) => {
        if (message.includes('Success')) {
          // Successful login, now redirect based on role
          const userRole = this.getRoleFromCookie();
          if (userRole === 'CITY-ADM') {
            this.router.navigate(['/city-admin-dashboard']);
          } else if (userRole === 'CLK') {
            this.router.navigate(['/clerk-dashboard']);
          } else if (userRole === 'SUP') {
            this.router.navigate(['/supervisor-dashboard']);
          }else if (userRole === 'MAYOR') {
            this.router.navigate(['/mayor-dashboard']);
          } else {
            alert('Login Failed');
            this.router.navigate(['/login']);
          }
        } else {
          // Display error message if login fails
          alert('Login Failed');
        }
      }
    );

  }

  // Get role from cookie
  getRoleFromCookie() {
    const name = "role=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookies = decodedCookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
      let cookie = cookies[i].trim();
      if (cookie.indexOf(name) === 0) {
        return cookie.substring(name.length, cookie.length);
      }
    }
    return "";
  }
}
