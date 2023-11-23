import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(private router:Router, private userService : UserService){}
  onLogOut(){
    localStorage.removeItem('isUserLoggedIn');
    localStorage.removeItem('username');
    this.router.navigate(['/login']);
  }
  setTabIndex(index:number){
    this.userService.setUserProfileTabIndex(index);
    this.router.navigate(['/profile']);
  }
}
