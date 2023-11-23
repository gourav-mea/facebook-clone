import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './sidebar/sidebar.component';
import { HomeComponent } from './home.component';
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatIconModule } from '@angular/material/icon';
import { AddCommentComponent, CommentComponent, FeedComponent } from './feed/feed.component';
import { AddPostComponent, AddPostDialog } from './add-post/add-post.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from '../app-routing.module';
import { LikePollingCountPipePipe } from '../like-polling-count-pipe.pipe';



@NgModule({
  declarations: [
    SidebarComponent,
    HomeComponent,
    FeedComponent,
    AddPostComponent,
    LikePollingCountPipePipe,
    AddCommentComponent,
    CommentComponent,
    AddPostDialog
  ],
  imports: [
    CommonModule,
    MatSidenavModule,
    AppRoutingModule,
    MatIconModule,
    ReactiveFormsModule
  ],
  exports: [
    SidebarComponent,
    HomeComponent,
    FeedComponent,
    AddPostComponent
  ]
})
export class HomeModule { }
