import { Component, EventEmitter, Input, Output } from '@angular/core';
import { LunchPlan } from '../lunch-plan';
import { Router } from '@angular/router';
import { LunchPlanService } from '../lunch-plan.service';

@Component({
  selector: 'app-plan-form',
  templateUrl: './plan-form.component.html',
  styleUrl: './plan-form.component.css'
})
export class PlanFormComponent {
  @Input() username : string;
  @Output() change = new EventEmitter<LunchPlan>();

  savedUsername: string;
  code: string = '';
  submitted: boolean = false;
  displayError: boolean = false;

  constructor(
    private router: Router,
    private lunchPlanService: LunchPlanService) {
      this.savedUsername = localStorage.getItem('username') || '';
    }
  
  onSubmit() {
    if (!this.isValidUsername(this.username)) {
      this.displayError = true;
    }
    this.lunchPlanService.create(this.username).subscribe({
      next: (data) => {
        this.change.emit(data);
        this.submitted = true;
        this.code = data.code;
        localStorage.setItem('username', this.username);
        this.goToLunchPlan();
      },
      error: (err) => this.displayError = true
    });
  }

  isValidUsername(code: string): boolean {
    const pattern = /^[a-zA-Z0-9]*$/; // Alphanumeric pattern
    return pattern.test(code);
  }

  goToLunchPlan() {
    this.router.navigate(['/plan/', this.code]);
    return;
  }
}
