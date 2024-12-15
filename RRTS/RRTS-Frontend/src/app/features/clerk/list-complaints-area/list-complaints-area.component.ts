import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ComplaintDetails } from '../../../shared/models/complaint-details.model';
import { ComplaintService } from '../../../core/services/complaint.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-list-complaints-area',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './list-complaints-area.component.html',
  styleUrl: './list-complaints-area.component.css'
})
export class ListComplaintsAreaComponent {
  area: string = '';
  complaints: ComplaintDetails[] = [];

  constructor(private complaintService: ComplaintService) { }

  fetchComplaintsByArea() {
    this.complaintService.getComplaintsByArea(this.area).subscribe(
      (data) => (this.complaints = data),
      (error) => console.error('Error fetching complaints by area', error)
    );
  }

}
