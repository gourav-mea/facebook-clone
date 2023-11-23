import { Component, OnInit } from '@angular/core';
import { FriendRequestService } from '../friend-request.service';
import { FriendRequest } from '../models/FriendRequest';
import { UserService } from '../user.service';
import { UserProfile } from '../models/UserProfile';
import { Router } from '@angular/router';

@Component({
  selector: 'app-friend-request',
  templateUrl: './friend-request.component.html',
  styleUrls: ['./friend-request.component.css']
})
export class FriendRequestComponent implements OnInit{
  
  constructor(private friendRequestService : FriendRequestService, private userService: UserService, private router : Router){}
  
  ngOnInit(): void {
    this.getFriendRequest();
  }

  friendRequests! : FriendRequest[];
  friendRequestUserProfiles!: UserProfile[];
  mergedFriendRequestList! : any;

  getFriendRequest(){
    this.friendRequestService.getFriendRequest().subscribe(
      success=>{
        this.friendRequests = success.filter((a)=>a.requestStatus=="PENDING");
        this.getFriendProfile();
      },
      error=>(console.log(error))
    );
  }

  getFriendProfile(){
    this.friendRequestUserProfiles = [];
    this.friendRequests.map((friendRequest)=>{
      this.userService.getUserProfile(friendRequest.sentFrom).subscribe(
        success=>{
          this.friendRequestUserProfiles.push(success);
          this.mergeUserProfile();
        },
        error=>(console.log(error))       
      )
    })
  }

  mergeUserProfile(){
    const mergedFriendRequestList = this.friendRequests.map((friendRequest)=>{
      const userProfile = this.friendRequestUserProfiles.find((profile)=> profile.userId == friendRequest.sentFrom);
      return {...friendRequest, ...userProfile};
    })
    this.mergedFriendRequestList = mergedFriendRequestList;
    console.log(this.mergedFriendRequestList);
  }

  acceptFriendRequest(id: number, sentTo: number, sentFrom: number){
    this.friendRequestService.acceptFriendRequest(id, sentTo, sentFrom).subscribe(
      success=>{
        console.log(success);
        window.location.reload();
        this.getFriendRequest();
      },
      error=>(console.log(error))
    )
  }

  rejectFriendRequest(id: number, sentTo: number, sentFrom: number){
    this.friendRequestService.rejectFriendRequest(id, sentTo, sentFrom).subscribe(
      success=>{
        console.log(success);
        window.location.reload();
        this.getFriendRequest();
      },
      error=>(console.log(error))
    )
  }

  navigateToSearch(){
    this.router.navigate(['/search-user']);
  }

}

