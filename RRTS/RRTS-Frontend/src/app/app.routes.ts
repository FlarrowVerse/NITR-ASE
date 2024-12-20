import { Routes } from '@angular/router';
import { LandingComponent } from './features/landing/landing.component';
import { LoginComponent } from './features/login/login.component';
import { EnquiryComponent } from './features/enquiry/enquiry.component';
import { authGuard } from './core/guards/auth.guard';
import { CityAdminDashboardComponent } from './features/dashboard/city-admin-dashboard/city-admin-dashboard.component';
import { SupervisorDashboardComponent } from './features/dashboard/supervisor-dashboard/supervisor-dashboard.component';
import { MayorDashboardComponent } from './features/dashboard/mayor-dashboard/mayor-dashboard.component';
import { ClerkDashboardComponent } from './features/clerk/clerk-dashboard/clerk-dashboard.component';
import { ErrorComponent } from './shared/components/error/error.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'enquiry', component: EnquiryComponent },
    { path: 'forbidden', component: ErrorComponent },
    { path: 'city-admin-dashboard', component: CityAdminDashboardComponent, canActivate: [authGuard], data: { role: 'CITY-ADM' } },
    { path: 'supervisor-dashboard', component: SupervisorDashboardComponent, canActivate: [authGuard], data: { role: 'SUP' } },
    { path: 'mayor-dashboard', component: MayorDashboardComponent, canActivate: [authGuard], data: { role: 'MAYOR' } },
    { 
        path: 'clerk-dashboard', 
        loadChildren: () => import('./features/clerk/clerk-routing.module').then(m => m.ClerkRoutingModule) 
    },
    { path: '', component: LandingComponent }, // Default route
];
