import { CanActivateFn, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const router = inject(Router);
  const cookieService = inject(CookieService);

  // Get the role from the cookie (or localStorage)
  const userRole = cookieService.get('role');

  // You can also use a service to retrieve the role if needed, for example: authService.getRole();

  if (!userRole) {
    // Redirect to login page if no role is found (i.e., the user is not logged in)
    router.navigate(['/login']);
    return false;
  }

  // Check if the user role matches the required role for this route
  const requiredRole = route.data['role'];  // If your routes are configured with data, e.g., { path: 'admin', data: { role: 'admin' }}

  if (requiredRole && userRole !== requiredRole) {
    // Redirect to an unauthorized page or show an error if the user doesn't have the right role
    router.navigate(['/unauthorized']);
    return false;
  }

  // Allow access to the route if the role is valid
  return true;
};
