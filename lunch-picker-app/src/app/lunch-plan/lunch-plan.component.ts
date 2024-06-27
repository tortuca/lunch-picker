import { Component } from '@angular/core';
import { LunchPlan } from './lunch-plan';

@Component({
  selector: 'app-lunch-plan',
  templateUrl: './lunch-plan.component.html'
})
export class LunchPlanComponent {
  plan: LunchPlan;
  username: string;

  constructor() {
      this.plan = new LunchPlan();
      this.username = localStorage.getItem('username') || '';
  }

  onNotifyCreate() {
    // this.plan = plan;
    console.log(this.plan);
  }

  onChangeUsername(event: Event) {
    this.username = localStorage.getItem('username') || '';
  }
}
