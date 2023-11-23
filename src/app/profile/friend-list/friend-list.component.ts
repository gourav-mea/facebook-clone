import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FriendRequestService } from 'src/app/friend-request.service';
import { UserProfile } from 'src/app/models/UserProfile';
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-friend-list',
  templateUrl: './friend-list.component.html',
  styleUrls: ['./friend-list.component.css']
})
export class FriendListComponent implements OnInit{
  
  constructor(private friendRequestService : FriendRequestService, private userService : UserService, private route: Router){}
  ngOnInit(): void {
    this.getFriendList()
  }
  friendIdList!: number[];
  friendList!: UserProfile[];

  getFriendList(){
    this.friendIdList = [];
    this.friendRequestService.getFriendList(localStorage.getItem('userId')).subscribe(
      success=>{
        this.friendIdList = success;
        this.getFriendProfileList();
      },
      error=>(
        console.log(error)
      )
    )
  }

  getFriendProfileList(){
    this.friendList = [];
    this.friendIdList.map((friendId)=>{
      this.userService.getUserProfile(friendId).subscribe(
        success=>{
          this.friendList.push(success);
          console.log(this.friendList);
        },
        error=>(console.log(error))
      )
    })
  }

  isFriendListEmpty(){
    if(this.friendList != null && this.friendList.length > 0){
      return true;
    }
    return false;
  }

  removeFriend(sentFrom: number){
    this.friendRequestService.removeFriend(localStorage.getItem('userId'), sentFrom).subscribe(
      success=>(
        console.log(success)
      ),
      error=>(
        console.log(error)
      )
    )
  }

  navigateToSearch(){
    this.route.navigate(['/search-user']);
  }
}
