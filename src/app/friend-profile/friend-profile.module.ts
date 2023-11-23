import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FriendProfileComponent } from './friend-profile.component';
import { AboutFriendComponent } from './about-friend/about-friend.component';
import { UserFriendListComponent } from './user-friend-list/user-friend-list.component';
import { FriendPhotosComponent } from './friend-photos/friend-photos.component';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from '../app-routing.module';

@NgModule({
  declarations: [
    FriendProfileComponent,
    AboutFriendComponent,
    UserFriendListComponent,
    FriendPhotosComponent
  ],
  imports: [
    CommonModule,
    MatIconModule,
    MatTabsModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule
  ],
  exports: [
    FriendProfileComponent,
    AboutFriendComponent,
    UserFriendListComponent,
    FriendPhotosComponent
  ]
})
export class FriendProfileModule { }
