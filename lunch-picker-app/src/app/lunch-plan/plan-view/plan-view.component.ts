import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { LunchPlanService } from '../lunch-plan.service';
import { LunchPlan } from '../lunch-plan';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { faHome, faShareSquare, faCopy } from '@fortawesome/free-solid-svg-icons';
import { WebSocketService } from '../../web-socket.service';
import { IMessage } from '@stomp/stompjs';

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
  disableClose: boolean = false;
  showLink: boolean = false;
  username = localStorage.getItem('username') || '';

  // font awesome
  faHome = faHome;
  faShareSquare = faShareSquare;
  faCopy = faCopy;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private lunchPlanService: LunchPlanService,
    private webSocketService: WebSocketService) {
      this.plan = new LunchPlan();
  }

  ngOnInit() {
    this.routeSubscription = this.route.params.subscribe( params => {
      const code = this.route.snapshot.params['code'];
      if (code !== undefined && code.length !== 0) {
        this.getPlan(code);
        this.subscribeWebSocket(code);
      }
    })
  }

  ngOnDestroy() {
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }

  /* API service calls */

  getPlan(code: string) {
    this.lunchPlanService.get(code).subscribe({
      next: data => {
        this.plan = data;
        this.choice = this.plan.choice;
        this.disableClose = !this.isPlanClosable();
      },
      error: err => {
        console.log(err);
        this.goHome();
      }
    });
  }

  closePlan() {
    this.lunchPlanService.close(this.plan?.code, this.username).subscribe({
      next: data => {
        this.plan = data;
        this.choice = this.plan.choice;
        this.disableClose = !this.isPlanClosable();
      },
      error: err => this.disableClose = true
    });
  }

  subscribeWebSocket(code: string) {
    let stompClient = this.webSocketService.connect();
    stompClient.connect({}, () => {
        stompClient.subscribe(`/topic/notify/${code}`, (data: IMessage) => {
            // console.log(data.body);
            this.plan = JSON.parse(data.body); 
        })
    });
  }

  /* Utility functions */

  isEmptyVenues() {
    return (this.plan?.venues.length !== 0);
  }
  
  isPlanClosable() {
    if (this.username === undefined || this.username.length === 0) {
      return false;
    }
    return this.plan?.active === true || this.plan?.choice === undefined;
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

  /* Navigation */

  goToLunchPlan() {
    this.router.navigate(['/plan/', this.plan.code]);
  }

  goHome() {
    this.router.navigate(['']);
  }

  onNotifyUpdate(plan: LunchPlan) {
    this.plan = plan;
  }

  onChangeUsername(event: Event) {
    this.username = localStorage.getItem('username') || '';
  }
}
