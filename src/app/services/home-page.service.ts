import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class HomePageService {
  baseUrl:string="http://localhost:9192/api/leave"
  constructor(private http: HttpClient) { }

  applyLeave(data:any){
    console.log(data)
    return this.http.post(this.baseUrl+"/apply",data);
  }

  getAllLeaves(){
    return this.http.get(this.baseUrl+"/all");
  }

  sendManagerResponse(data:any){
    return this.http.put(this.baseUrl+"/manager-approval",data);
  }

}
