import { Component } from '@angular/core';
import { NavbarComponent } from "../../shared/components/navbar/navbar.component";
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ComplaintService } from '../../core/services/complaint.service';
import { Complaint } from '../../shared/models/complaint.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-enquiry',
  standalone: true,
  imports: [NavbarComponent, ReactiveFormsModule, CommonModule],
  templateUrl: './enquiry.component.html',
  styleUrl: './enquiry.component.css'
})
export class EnquiryComponent {
  complaintData = new FormGroup({
    id: new FormControl('', Validators.required)
  });

  complaintStatus: Complaint | null = null;

  constructor(private router: Router, private complaintService: ComplaintService) {
  }

  handleEnquiry(): void {
    const complaintID = this.complaintData.value.id || '';

    this.complaintService.getComplaintStatusById(complaintID).subscribe({
      next: (result: Complaint) => {
        this.complaintStatus = result; // Success callback
      },
      error: (error) => {
        this.complaintStatus = null; // Error callback
      },
      complete: () => {
        console.log('Request completed'); // Optional complete callback
      }
    });
    
  }
}
