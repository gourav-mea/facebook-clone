import { Component } from '@angular/core';
import { UserProfile } from '../models/UserProfile';
import { UserService } from '../user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FriendRequestService } from '../friend-request.service';

@Component({
  selector: 'app-friend-profile',
  templateUrl: './friend-profile.component.html',
  styleUrls: ['./friend-profile.component.css']
})
export class FriendProfileComponent {
  friendProfile!: UserProfile;
  friendUserId! : number;
  friendStatus! : string;
  constructor(public userService: UserService, private router : Router, private route: ActivatedRoute, private friendRequestService : FriendRequestService){}

  ngOnInit(): void {
    this.route.params.subscribe(
      param=>{
        this.friendUserId = param['id'];
        if(this.friendUserId==this.convertStringOrNullToNumber(localStorage.getItem('userId'))){
          this.router.navigate(['/profile']);
        }
      }
    )
    
    this.getFriendProfile();
    this.getFriendshipStatus();
  }

  getFriendProfile(){
    this.userService.getFriendProfile(this.friendUserId ,localStorage.getItem('userId')).subscribe(
      (success)=>{
        this.friendProfile = success;
        console.log("friendProfile", this.friendProfile);
      },
      (error)=>(console.log(error))
    )
  }

  getFriendshipStatus(){
    this.friendRequestService.getStatus(localStorage.getItem('userId'), this.friendUserId).subscribe(
      (success)=>{
        this.friendStatus = success.message;
        console.log(this.friendStatus);
      },
      error=>(console.log(error))
    )
  }

  checkStatus(num : number): boolean{
    if(num==1 && this.friendStatus == "ACCEPTED" ){
      return true;
    }
    if(num==2 && this.friendStatus == "NOT_FRIEND"){
      return true;
    }
    if(num==3 && this.friendStatus == "PENDING"){
        return true;
    }
    return false;
  }

  removeFriend(){
    this.friendRequestService.removeFriend(localStorage.getItem('userId'), this.friendUserId).subscribe(
      success=>{
        window.location.reload();
        console.log(success);
      },
      error=>(
        console.log(error)
      )
    )
  }

  removeFriendRequest(){
    this.friendRequestService.rejectFriendRequest(1, this.friendUserId, this.convertStringOrNullToNumber(localStorage.getItem('userId'))).subscribe(
      success=>{
        console.log(success);
        // reload here
        window.location.reload();
      },
      error=>(console.log(error))
    )
  }

  addFriend(){
    this.friendRequestService.addFriendRequest(1, this.friendUserId, this.convertStringOrNullToNumber(localStorage.getItem('userId'))).subscribe(
      success=>{
        console.log(success);
        // reload here
        window.location.reload();
      },
      error=>(console.log(error))
    )
  }

  convertStringOrNullToNumber(input: string | null): number {
    if (input === null) {
      return NaN;
    } 
    const numberValue = +input;
    if (isNaN(numberValue)) {
      return NaN;
    }
    return numberValue;
  }

}
