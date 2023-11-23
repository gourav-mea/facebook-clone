import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserProfile } from 'src/app/models/UserProfile';
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit{
  userProfile!: UserProfile;
  constructor(public userService: UserService, private router : Router){}

  ngOnInit(): void {
    this.getUserProfile();
  }

  getUserProfile(){
    this.userService.getUserProfile(localStorage.getItem('userId')).subscribe(
      (success)=>{
        this.userProfile = success;
      },
      (error)=>(console.log(error))
    )
  }

  onLogOut(){
    localStorage.removeItem('isUserLoggedIn');
    localStorage.removeItem('username');
    this.router.navigate(['/login']);
  }
  onEditClick(){
    this.userService.setUserProfileTabIndex(2);
    this.router.navigate(['/profile']);
  }
}
