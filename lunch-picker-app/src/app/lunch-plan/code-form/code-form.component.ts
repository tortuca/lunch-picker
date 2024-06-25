import { Component } from '@angular/core';
import { LunchPlanService } from '../lunch-plan.service';
import { LunchPlan } from '../lunch-plan';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-code-form',
  templateUrl: './code-form.component.html',
  styleUrl: './code-form.component.css'
})
export class CodeFormComponent {

  plan: LunchPlan;
  displayError: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private lunchPlanService: LunchPlanService) {
      this.plan = new LunchPlan();     
    }
  
  onSubmit() {
    this.lunchPlanService.get(this.plan.code).subscribe(
      () => this.goToLunchPlan(),
      err => this.displayError = true
    );
  }

  goToLunchPlan() {
    this.router.navigate(['/plan/', this.plan.code]);
  }
}
