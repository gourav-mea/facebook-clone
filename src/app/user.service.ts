import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserProfile } from './models/UserProfile';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userProfileTabIndexSubject = new BehaviorSubject<number>(0);
  userProfileTabIndex$ : Observable<number> = this.userProfileTabIndexSubject.asObservable();

  setUserProfileTabIndex(index : number){
    this.userProfileTabIndexSubject.next(index);
  }

  constructor(private http:HttpClient) { }

  getAllUser():Observable<any>{
    let url="http://localhost:4000/user/alluser";
    return this.http.get<any>(url);
  }
  registerUser(userData: any): Observable<any> {
    let url="http://localhost:4000/user/register";
    return this.http.post<any>(url,userData);
  }
  loginUser(userLoginData:any):Observable<any>{
    let url="http://localhost:4000/user/login";
    return this.http.post<any>(url,userLoginData);
  }
  forgotPassword(userForgetData:any):Observable<any>{
    let url="http://localhost:4000/user/forgot-password";
    return this.http.put<any>(url,userForgetData);
  }
  getUserDetail(username:any):Observable<any>{
    let url="http://localhost:4000/user/"+username;
    return this.http.get(url);
  }

  getUserProfile(userId : any): Observable<UserProfile>{
    let url="http://localhost:4000/user/profile/view?userId="+ userId;
    return this.http.get<UserProfile>(url);
  }

  getFriendProfile(friendUserId : any, userId : any): Observable<UserProfile>{
    let url="http://localhost:4000/user/profile/viewfriend?friendUserId="+ friendUserId + "&userId=" + userId;
    return this.http.get<UserProfile>(url);
  }

  getAllUserProfileBySearchString(searchString : string | null): Observable<UserProfile[]>{
    let url="http://localhost:4000/user/profile/search?searchString="+ searchString;
    return this.http.get<UserProfile[]>(url);
  }

  saveUserProfile(userProfile: any): Observable<any>{
    let url="http://localhost:4000/user/profile/save";
    return this.http.post<any>(url, userProfile);
  }
}
