import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FriendRequest } from './models/FriendRequest';

@Injectable({
  providedIn: 'root'
})
export class FriendRequestService {
  constructor(private http : HttpClient) { }
  
  getFriendRequest():Observable<FriendRequest[]>{
    let url="http://localhost:2000/friendrequest/get/sentto?userId=" + localStorage.getItem('userId');
    return this.http.get<FriendRequest[]>(url);
  }

  acceptFriendRequest(id: number, sentTo: number, sentFrom: number):Observable<string>{
    let url="http://localhost:2000/friendrequest/accept";
    return this.http.put<string>(url,{
      'id': id,
      'sentTo':sentTo,
      'sentFrom': sentFrom
    });
  }

  rejectFriendRequest(id: number, sentTo: number, sentFrom: number):Observable<string>{
    let url="http://localhost:2000/friendrequest/reject";
    return this.http.put<string>(url, {
      'id': id,
      'sentTo':sentTo,
      'sentFrom': sentFrom
    });
  }

  addFriendRequest(id: number, sentTo: number, sentFrom: number):Observable<any>{
    let url="http://localhost:2000/friendrequest/add";
    return this.http.post<any>(url, {
      'id': id,
      'sentTo':sentTo,
      'sentFrom': sentFrom
    });
  }

  getFriendList(userId:string|null):Observable<number[]>{
    let url="http://localhost:2000/friendrequest/friendlist?userId=" + userId;
    return this.http.get<number[]>(url);
  }

  getUserFriendList(friendUserId: number, userId: string|null):Observable<number[]>{
    let url="http://localhost:2000/friendrequest/friend/friendlist?friendUserId=" + friendUserId + "&userId=" + userId;
    return this.http.get<number[]>(url);
  }

  removeFriend(userId: string|null, friendUserId: number):Observable<any>{
    let url="http://localhost:2000/friendrequest/removefriend?userId=" + userId+"&friendUserId="+friendUserId;
    return this.http.put<any>(url,{});
  }

  getStatus(userId: string|null, friendUserId: number):Observable<any>{
    let url="http://localhost:2000/friendrequest/getstatus?userId=" + userId+"&friendUserId="+friendUserId;
    return this.http.get<any>(url);
  }


}
