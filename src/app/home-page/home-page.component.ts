import { Component } from '@angular/core';
import { HomePageService } from '../services/home-page.service';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent {
  leaveApplicationList:any;
  formData = { empId: '', leavesRequested: null };

  constructor(private homePageService: HomePageService){

  }

  ngOnInit(){
    this.homePageService.getAllLeaves().subscribe(data=>{
      this.leaveApplicationList = data;
    }, error=>{
      console.log(error);
    })
  }

  createNewLeaveRequest(){
    console.log(this.formData)
    this.homePageService.applyLeave(this.formData).subscribe(data=>{
      console.log(data);
      this.ngOnInit()
    }, error=>{
      console.log(error)
    })
  }

  clearForm(){
    this.formData.empId='';
    this.formData.leavesRequested=null
  }
}
