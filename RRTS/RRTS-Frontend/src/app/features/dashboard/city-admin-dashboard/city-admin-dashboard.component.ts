import { Component } from '@angular/core';
import { NavbarComponent } from "../../../shared/components/navbar/navbar.component";

@Component({
  selector: 'app-city-admin-dashboard',
  standalone: true,
  imports: [NavbarComponent],
  templateUrl: './city-admin-dashboard.component.html',
  styleUrl: './city-admin-dashboard.component.css'
})
export class CityAdminDashboardComponent {

}
