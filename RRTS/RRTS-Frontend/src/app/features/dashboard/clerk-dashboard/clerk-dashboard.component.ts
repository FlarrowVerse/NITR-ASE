import { Component } from '@angular/core';
import { NavbarComponent } from "../../../shared/components/navbar/navbar.component";

@Component({
  selector: 'app-clerk-dashboard',
  standalone: true,
  imports: [NavbarComponent],
  templateUrl: './clerk-dashboard.component.html',
  styleUrl: './clerk-dashboard.component.css'
})
export class ClerkDashboardComponent {

}
