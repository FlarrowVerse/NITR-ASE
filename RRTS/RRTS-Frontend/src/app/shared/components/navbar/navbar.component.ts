import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  isAuthenticated: boolean = false;

  constructor(private cookieService: CookieService, private authService: AuthService) {}

  ngOnInit(): void {
    this.isAuthenticated = this.cookieService.check('role');
  }

  onLogout(): void {
    // Make an API call to the server to invalidate the token
    this.authService.logout();
    this.isAuthenticated = false;
  }
}
