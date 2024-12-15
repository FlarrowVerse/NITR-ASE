import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiConfigService } from './api-config.service';
import { Router } from '@angular/router';
import { catchError, map, Observable, of } from 'rxjs';
import { Complaint } from '../../shared/models/complaint.model';
import { ComplaintDetails } from '../../shared/models/complaint-details.model';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {
  
  private baseUrl: string;

  constructor(private http: HttpClient, private apiConfig: ApiConfigService, private router: Router) {
    this.baseUrl = this.apiConfig.apiUrl;
  }

  getComplaintStatusById(id: string): Observable<Complaint> {

    const fetchUrl = `${this.baseUrl}/complaints/status/${id}`;

    return this.http.get(fetchUrl, { responseType: 'text' }).pipe(
      map((response: any) => {
        // Map the response to a Complaint object
        const complaint: Complaint = {
          id: id,
          status: response
        };
        return complaint; // Return the transformed Complaint
      }),
      catchError((error) => {
        console.error('Error fetching complaint:', error); // Log the error for debugging
        // Return a default or empty complaint object
        return of({ id: '', status: 'Error: Complaint not found' } as Complaint);
      })
    );
  }

  registerComplaint(value: Partial<{ description: string | null; location: string | null; }>): Observable<String> {
    throw new Error('Method not implemented.');
  }

  getAllComplaints(): Observable<ComplaintDetails[]> {
    throw new Error('Method not implemented.');
  }

  getComplaintsByArea(area: string): Observable<ComplaintDetails[]> {
    throw new Error('Method not implemented.');
  }
}
