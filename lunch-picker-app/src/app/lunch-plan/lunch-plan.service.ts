import { Injectable } from '@angular/core';
import { LunchPlan } from './lunch-plan';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LunchPlanService {
  private apiUrl: string;

  constructor(private http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/plan';
  }

  public create() {
    return this.http.post<LunchPlan>(this.apiUrl, {});
  }

  public get(code: string): Observable<LunchPlan> {
    const url =  `${this.apiUrl}/${code}`;
    return this.http.get<LunchPlan>(url);
  }

  public addVenue(code: string, venue: string) {
    const url = `${this.apiUrl}/${code}/add`;
    return this.http.post<LunchPlan>(url, venue, {});
  }

  public close(code: string) {
    const url = `${this.apiUrl}/${code}/close`;
    return this.http.post<LunchPlan>(url, {});
  }
}
