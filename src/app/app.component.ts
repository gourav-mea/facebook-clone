import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'infy_facebook';
  isLoggedIn(){
    const returnValue = localStorage.getItem("isUserLoggedIn")!=null? true : false;
    return returnValue;
  }
}
