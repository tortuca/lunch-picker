import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LunchPlanService } from '../lunch-plan.service';
import { LunchPlan } from '../lunch-plan';

@Component({
  selector: 'app-code-form',
  templateUrl: './code-form.component.html',
  styleUrl: './code-form.component.css'
})
export class CodeFormComponent {

  plan: LunchPlan;
  displayError = false;

  constructor(
    private router: Router,
    private lunchPlanService: LunchPlanService) {
      this.plan = new LunchPlan();     
    }
  
  onSubmit() {
    if (!this.isValidCode(this.plan?.code)) {
      this.displayError = true;
    }
    this.lunchPlanService.get(this.plan?.code).subscribe({
      next: () => this.goToLunchPlan(),
      error: () => this.displayError = true
    });
  }

  isValidCode(code: string): boolean {
    const codePattern = /^[a-zA-Z0-9]*$/; // Alphanumeric pattern
    return codePattern.test(code);
  }

  goToLunchPlan() {
    this.router.navigate(['/plan/', this.plan.code]);
  }
}
