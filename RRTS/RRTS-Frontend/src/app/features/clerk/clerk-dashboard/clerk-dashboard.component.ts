import { Component } from '@angular/core';
import { NavbarComponent } from "../../../shared/components/navbar/navbar.component";
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-clerk-dashboard',
  standalone: true,
  imports: [NavbarComponent, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './clerk-dashboard.component.html',
  styleUrl: './clerk-dashboard.component.css'
})
export class ClerkDashboardComponent {

}
