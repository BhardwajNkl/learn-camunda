import { Component } from '@angular/core';
import { HomePageService } from '../services/home-page.service';
@Component({
  selector: 'app-manager-page',
  templateUrl: './manager-page.component.html',
  styleUrls: ['./manager-page.component.css']
})
export class ManagerPageComponent {
  leaveApplicationList:any;

  constructor(private homePageService: HomePageService){

  }

  ngOnInit(){
    this.homePageService.getAllLeaves().subscribe(data=>{
      this.leaveApplicationList = data;
    }, error=>{
      console.log(error);
    })
  }

  sendManagerResponse(leaveId:number, approvalStatus:boolean){
    const managerResponse = {leaveId, approvalStatus};
    this.homePageService.sendManagerResponse(managerResponse).subscribe(data=>{
      console.log(data);
      this.ngOnInit();
    }, error=>{
      console.log(error);
    })
  }
}
