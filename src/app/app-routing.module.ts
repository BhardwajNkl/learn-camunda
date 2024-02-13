import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { ManagerPageComponent } from './manager-page/manager-page.component';

const routes: Routes = [
  {
    path:"",
    component:HomePageComponent
  },
  {
    path:"manager",
    component:ManagerPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
