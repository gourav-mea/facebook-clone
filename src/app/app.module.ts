import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatIconModule } from '@angular/material/icon';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserModule } from './user/user.module';
import { PostModule } from './post/post.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'
import { UserService } from './user.service';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeModule } from './home/home.module';
import { ProfileModule } from './profile/profile.module';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialogModule } from '@angular/material/dialog';

import { SearchUserComponent } from './search-user/search-user.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FriendProfileModule } from './friend-profile/friend-profile.module';
import { FriendRequestComponent } from './friend-request/friend-request.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FriendRequestComponent,
    SearchUserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    UserModule,
    PostModule,
    HomeModule,
    ProfileModule,
    MatIconModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule,
    MatDialogModule,
    FriendProfileModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ],
  exports: [NavbarComponent],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
