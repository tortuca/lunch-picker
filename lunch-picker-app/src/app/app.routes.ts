import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { PlanViewComponent } from './lunch-plan/plan-view/plan-view.component';
import { LunchPlanComponent } from './lunch-plan/lunch-plan.component';

export const routes: Routes = [
    { path: '', component: LunchPlanComponent },
    { path: '*', component: LunchPlanComponent },
    { path: 'plan/:code', component: PlanViewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
