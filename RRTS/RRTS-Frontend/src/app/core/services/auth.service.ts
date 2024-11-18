import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiConfigService } from './api-config.service';
import { Router } from '@angular/router';
import { LoginRequest } from '../../shared/models/login-request.model';
import { of } from 'rxjs/internal/observable/of';
import { map } from 'rxjs/internal/operators/map';
import { catchError, Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl: string;

  constructor(private http: HttpClient, private apiConfig: ApiConfigService, private router: Router, private cookieService: CookieService) {
    this.baseUrl = this.apiConfig.apiUrl;
  }

  login(loginRequest: LoginRequest): Observable<string> {
    const loginUrl = `${this.baseUrl}/auth/login`;

    return this.http.post<any>(loginUrl, loginRequest, { withCredentials: true }).pipe(
      map((response: any) => {
          return 'Success';
      }), catchError(error => {
        return of('Failure' + error);
      }));
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  }

  logout(): void {
    const logoutUrl = `${this.baseUrl}/auth/logout`;
    // Make an API call to the server to invalidate the token
    this.http.get(logoutUrl).subscribe({
      next: () => {
        // On successful logout, remove the token from cookies and update the authenticated state
        this.cookieService.delete('role', '/');
        this.router.navigate(['/login']); // Navigate to the login page
      },
      error: (err) => {
        console.error('Logout failed', err);
      }
    });
  }

  extractToken(token: string) {
    const parts = token.split('.');
  
  if (parts.length !== 3) {
    throw new Error('Invalid JWT token');
  }
  
  // Decode the payload (second part of the token)
  const payloadBase64 = parts[1].replace(/-/g, '+').replace(/_/g, '/');
  const payloadDecoded = atob(payloadBase64);

  // Parse the JSON string to get an object
  const payload = JSON.parse(payloadDecoded);

  return payload;
  }
}
