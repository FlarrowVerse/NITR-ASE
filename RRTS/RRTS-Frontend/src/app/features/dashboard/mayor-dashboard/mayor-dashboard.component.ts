import { Component } from '@angular/core';
import { NavbarComponent } from "../../../shared/components/navbar/navbar.component";

@Component({
  selector: 'app-mayor-dashboard',
  standalone: true,
  imports: [NavbarComponent],
  templateUrl: './mayor-dashboard.component.html',
  styleUrl: './mayor-dashboard.component.css'
})
export class MayorDashboardComponent {

}
