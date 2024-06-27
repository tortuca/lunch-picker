import { Injectable } from '@angular/core';
import { LunchPlan } from './lunch-plan';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class LunchPlanService {
  private apiUrl: string;

  constructor(private http: HttpClient) {
    this.apiUrl = `${environment.apiUrl}/plan`;
  }

  public create(username: string) {
    return this.http.post<LunchPlan>(this.apiUrl, username, {});
  }

  public get(code: string): Observable<LunchPlan> {
    const url =  `${this.apiUrl}/${code}`;
    return this.http.get<LunchPlan>(url);
  }

  public addVenue(code: string, venue: string) {
    const url = `${this.apiUrl}/${code}/add`;
    return this.http.post<LunchPlan>(url, venue, {});
  }

  public close(code: string, username: string) {
    const url = `${this.apiUrl}/${code}/close`;
    return this.http.post<LunchPlan>(url, username, {});
  }
}
