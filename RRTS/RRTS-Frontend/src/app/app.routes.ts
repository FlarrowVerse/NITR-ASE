import { Routes } from '@angular/router';
import { LandingComponent } from './features/landing/landing.component';
import { LoginComponent } from './features/login/login.component';
import { EnquiryComponent } from './features/enquiry/enquiry.component';
import { authGuard } from './core/guards/auth.guard';
import { CityAdminDashboardComponent } from './features/dashboard/city-admin-dashboard/city-admin-dashboard.component';
import { SupervisorDashboardComponent } from './features/dashboard/supervisor-dashboard/supervisor-dashboard.component';
import { MayorDashboardComponent } from './features/dashboard/mayor-dashboard/mayor-dashboard.component';
import { ClerkDashboardComponent } from './features/dashboard/clerk-dashboard/clerk-dashboard.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'enquiry', component: EnquiryComponent },
    { path: 'forbidden', component: ClerkDashboardComponent },
    { path: 'city-admin-dashboard', component: CityAdminDashboardComponent, canActivate: [authGuard], data: { role: 'CITY-ADM' } },
    { path: 'supervisor-dashboard', component: SupervisorDashboardComponent, canActivate: [authGuard], data: { role: 'SUP' } },
    { path: 'mayor-dashboard', component: MayorDashboardComponent, canActivate: [authGuard], data: { role: 'MAYOR' } },
    { path: 'clerk-dashboard', component: ClerkDashboardComponent, canActivate: [authGuard], data: { role: 'CLK' } },
    { path: '', component: LandingComponent }, // Default route
];
