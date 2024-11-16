import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiConfigService } from './api-config.service';
import { Router } from '@angular/router';
import { LoginRequest } from '../../shared/models/login-request.model';
import { of } from 'rxjs/internal/observable/of';
import { map } from 'rxjs/internal/operators/map';
import { catchError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl: string;

  constructor(private http: HttpClient, private apiConfig: ApiConfigService, private router: Router) {
    this.baseUrl = this.apiConfig.apiUrl;
  }

  login(loginRequest: LoginRequest): Observable<string> {
    const loginUrl = `${this.baseUrl}/auth/login`;

    return this.http.post<any>(loginUrl, loginRequest, { withCredentials: true }).pipe(
      map((response: any) => {
          const token = response.token;
          this.setRoleCookie(token);
          return 'Success';
      }), catchError(error => {
        return of('Failure' + error);
      }));
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  }

  logout(): void {
    this.router.navigate(['/login']);
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

  setRoleCookie(token:string) {
    const payload = this.extractToken(token);
  
    if (payload && payload.role) {
      // Store the role in a cookie (HttpOnly and Secure flag are set by the server)
      // Here, we're setting it from the client side, but ideally, HttpOnly and Secure should be set by the server.
      const role = payload.role;
      const expires = new Date();
      expires.setTime(expires.getTime() + (60 * 60 * 1000)); // Cookie expiry time (1 hour)
  
      // Setting cookie
      document.cookie = `role=${role};expires=${expires.toUTCString()};path=/;Secure;SameSite=Strict`;
    }
  }
}
