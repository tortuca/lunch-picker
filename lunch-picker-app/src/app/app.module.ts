import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app.routes';
import { AppComponent } from './app.component';
import { CodeFormComponent } from './lunch-plan/code-form/code-form.component';
import { PlanFormComponent } from './lunch-plan/plan-form/plan-form.component';
import { VenueFormComponent } from './lunch-plan/venue-form/venue-form.component';
import { PlanViewComponent } from './lunch-plan/plan-view/plan-view.component';
import { LunchPlanService } from './lunch-plan/lunch-plan.service';
import { LunchPlanComponent } from './lunch-plan/lunch-plan.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ModalShareComponent } from './lunch-plan/modal-share/modal-share.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
    AppRoutingModule,
    NgbModule,
    FontAwesomeModule
  ],
  declarations: [
    AppComponent,
    LunchPlanComponent,
    CodeFormComponent,
    VenueFormComponent,
    PlanFormComponent,
    PlanViewComponent,
    ModalShareComponent
  ],
  providers: [LunchPlanService],
  bootstrap: [AppComponent]
})
export class AppModule { }