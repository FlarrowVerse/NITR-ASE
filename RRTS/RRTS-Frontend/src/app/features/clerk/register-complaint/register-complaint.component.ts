import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ComplaintService } from '../../../core/services/complaint.service';

@Component({
  selector: 'app-register-complaint',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './register-complaint.component.html',
  styleUrl: './register-complaint.component.css'
})
export class RegisterComplaintComponent {
  complaintForm = new FormGroup({
    description: new FormControl('', Validators.required),
    location: new FormControl('', Validators.required),
  });

  constructor(private complaintService: ComplaintService) {}

  submitComplaint() {
    this.complaintService.registerComplaint(this.complaintForm.value).subscribe(
      () => alert('Complaint registered successfully'),
      (error: any) => console.error('Error registering complaint', error)
    );
  }
}
