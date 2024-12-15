import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClerkDashboardComponent } from './clerk-dashboard/clerk-dashboard.component';
import { RegisterComplaintComponent } from './register-complaint/register-complaint.component';
import { ListComplaintsAreaComponent } from './list-complaints-area/list-complaints-area.component';
import { ListAllComplaintsComponent } from './list-all-complaints/list-all-complaints.component';
import { authGuard } from '../../core/guards/auth.guard';


const routes: Routes = [
    {
        path: '',
        component: ClerkDashboardComponent, // Standalone component
        canActivate: [authGuard],
        data: { role: 'CLK' },
        children: [
            { path: 'register-complaint', component: RegisterComplaintComponent }, // Standalone component
            { path: 'list-complaints', component: ListAllComplaintsComponent }, // Standalone component
            { path: 'list-complaints-area', component: ListComplaintsAreaComponent } // Standalone component
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ClerkRoutingModule { }