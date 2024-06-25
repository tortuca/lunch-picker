import { Component } from '@angular/core';
import { LunchPlan } from './lunch-plan';

@Component({
  selector: 'app-lunch-plan',
  templateUrl: './lunch-plan.component.html'
})
export class LunchPlanComponent {
  plan: LunchPlan;

  constructor() {
      this.plan = new LunchPlan();
  }

  onNotifyCreate() {
    // this.plan = plan;
    console.log(this.plan);
  }
}
