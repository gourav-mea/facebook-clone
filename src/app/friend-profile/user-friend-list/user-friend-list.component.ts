import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FriendRequestService } from 'src/app/friend-request.service';
import { UserProfile } from 'src/app/models/UserProfile';
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-user-friend-list',
  templateUrl: './user-friend-list.component.html',
  styleUrls: ['./user-friend-list.component.css']
})
export class UserFriendListComponent implements OnInit{
  friendUserId! : number;
  constructor(private friendRequestService : FriendRequestService, private userService : UserService, private route: Router, private router: ActivatedRoute){}
  
  ngOnInit(): void {
    this.router.params.subscribe(
      param=>(
        this.friendUserId = param['id']
      )
    )
    this.getFriendFriendList()
  }
  friendIdList!: number[];
  friendList!: UserProfile[];

  getFriendFriendList(){
    this.friendIdList = [];
    this.friendRequestService.getUserFriendList(this.friendUserId ,localStorage.getItem('userId')).subscribe(
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


}
