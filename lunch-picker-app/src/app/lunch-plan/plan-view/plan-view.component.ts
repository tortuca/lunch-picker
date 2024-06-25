import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { LunchPlanService } from '../lunch-plan.service';
import { LunchPlan } from '../lunch-plan';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { faHome, faShareSquare, faCopy } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-plan-view',
  templateUrl: './plan-view.component.html',
  styleUrl: './plan-view.component.css'
})
export class PlanViewComponent implements OnInit {
  @Input() plan: LunchPlan;
  routeSubscription: Subscription;

  venue: string = '';
  choice: string = '';
  url: string = '';
  enableClose: boolean = false;
  showLink: boolean = false;

  // font awesome
  faHome = faHome;
  faShareSquare = faShareSquare;
  faCopy = faCopy;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private lunchPlanService: LunchPlanService) {
      this.plan = new LunchPlan();
  }

  ngOnInit() {
    this.routeSubscription = this.route.params.subscribe( params => {
      const code = this.route.snapshot.params['code'];
      if (code !== undefined && code.length !== 0) {
        this.getPlan(code);
        this.enableClose = this.isValidPlan();
      }
    })
  }

  ngOnDestroy() {
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }

  getPlan(code: string) {
    this.lunchPlanService.get(code).subscribe({
      next: data => {
        this.plan = data;
        this.choice = this.plan.choice;
      },
      error: err => console.log(err)
    });
  }

  closePlan() {
    this.lunchPlanService.close(this.plan?.code).subscribe({
      next: data => {
        this.choice = data.choice;
        this.enableClose = this.isValidPlan();
      },
      error: err => console.log(err)
    });
  }

  goToLunchPlan() {
    this.router.navigate(['/plan/', this.plan.code]);
  }

  goHome() {
    this.router.navigate(['']);
  }

  isEmptyVenues() {
    return (this.plan.venues.length !== 0);
  }
  
  isValidPlan() {
    return this.plan.active || this.choice === undefined;
  }

  revealLink() {
    this.url = this.getCurrentUrl();
    this.showLink = true;
  }
  
  getCurrentUrl(): string {
    return window.location.href;
  }

  copyLink() {
    navigator.clipboard.writeText(this.url).then(
      () => {
        console.log('URL copied to clipboard');
        this.showLink = false;
      },
      (err) => console.error('Could not copy URL: ', err)
    );
  }

  onNotifyUpdate(plan: LunchPlan) {
    this.plan = plan;
  }
}
