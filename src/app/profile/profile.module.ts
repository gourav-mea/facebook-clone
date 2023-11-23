import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { UserPhotosComponent } from './user-photos/user-photos.component';
import { FriendListComponent } from './friend-list/friend-list.component';
import { AboutMeComponent } from './about-me/about-me.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from '../app-routing.module';

@NgModule({
  declarations: [
    UserProfileComponent,
    UserPhotosComponent,
    FriendListComponent,
    AboutMeComponent
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
    UserProfileComponent,
    UserPhotosComponent,
    FriendListComponent,
    AboutMeComponent
  ]
})
export class ProfileModule { }
