import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LunchPlanService } from '../lunch-plan.service';
import { LunchPlan } from '../lunch-plan';

@Component({
  selector: 'app-venue-form',
  templateUrl: './venue-form.component.html',
  styleUrl: './venue-form.component.css'
})
export class VenueFormComponent {
  @Input() plan: LunchPlan;
  @Output() update = new EventEmitter<LunchPlan>();
  newVenue: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private lunchPlanService: LunchPlanService) {
    }

  onSubmit() {
    if (this.newVenue.length === 0) return;
    this.lunchPlanService.addVenue(this.plan.code, this.newVenue).subscribe({
      next: (data) => {
        // this.goToLunchPlan();
        this.update.emit(data);
        this.newVenue = '';
      },
      error: (err) => console.log(err)
    });
  }

  goToLunchPlan() {
    this.router.navigate(['/plan/', this.plan.code]);
    return;
  }
}
