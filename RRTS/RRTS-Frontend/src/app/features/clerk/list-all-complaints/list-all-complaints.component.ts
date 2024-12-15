import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ComplaintService } from '../../../core/services/complaint.service';
import { ComplaintDetails } from '../../../shared/models/complaint-details.model';

@Component({
  selector: 'app-list-all-complaints',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './list-all-complaints.component.html',
  styleUrl: './list-all-complaints.component.css'
})
export class ListAllComplaintsComponent {
  complaints: ComplaintDetails[] = [];

  constructor(private complaintService: ComplaintService) {}

  ngOnInit() {
    this.complaintService.getAllComplaints().subscribe({
      next: (data) => (this.complaints = data),
      error: (error) => console.error('Error fetching complaints', error),
      complete: () => console.log('Fetch complaints completed')
    });
  }
}
