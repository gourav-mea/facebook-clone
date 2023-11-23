import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './user/login/login.component';
import { HomeComponent } from './home/home.component';
import { LoginGuardService } from './login-guard.service';
import { UserProfileComponent } from './profile/user-profile/user-profile.component';
import { SearchUserComponent } from './search-user/search-user.component';
import { FriendProfileComponent } from './friend-profile/friend-profile.component';
import { FriendRequestComponent } from './friend-request/friend-request.component';


const routes: Routes = [
  {path:'',redirectTo:'home',pathMatch:'full'},
  {path:'home', component: HomeComponent, canActivate: [LoginGuardService]},
  {path:'login',component: LoginComponent},
  {path:'friend-request',component: FriendRequestComponent, canActivate: [LoginGuardService]},
  {path:'search-user', component: SearchUserComponent, canActivate: [LoginGuardService]},
  {path:'post', loadChildren: ()=> import('./post/post.module').then(m=>m.PostModule), canActivate: [LoginGuardService]},
  {path:'profile',component:UserProfileComponent, canActivate: [LoginGuardService]},
  {path:'friendprofile/:id',component:FriendProfileComponent, canActivate: [LoginGuardService]},
  {path:'**',redirectTo:'home',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})



export class AppRoutingModule { }
