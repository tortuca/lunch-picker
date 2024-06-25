import { Component, EventEmitter, Output } from '@angular/core';
import { LunchPlan } from '../lunch-plan';
import { ActivatedRoute, Router } from '@angular/router';
import { LunchPlanService } from '../lunch-plan.service';

@Component({
  selector: 'app-plan-form',
  templateUrl: './plan-form.component.html',
  styleUrl: './plan-form.component.css'
})
export class PlanFormComponent {
  @Output() change = new EventEmitter<LunchPlan>();

  code: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private lunchPlanService: LunchPlanService) {
    }
  
  onSubmit() {
    this.lunchPlanService.create().subscribe(result => {
      this.change.emit(result);
      this.code = result.code;
      // console.log(this.code);
      this.goToLunchPlan();
    });
  }

  goToLunchPlan() {
    this.router.navigate(['/plan/', this.code]);
    return;
  }
}
