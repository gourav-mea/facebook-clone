import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserPostComponent } from './user-post/user-post.component';
import { PostRoutingModule } from './post-routing.module';



@NgModule({
  declarations: [
    UserPostComponent
  ],
  imports: [
    CommonModule,
    PostRoutingModule
  ],
  exports: [
    UserPostComponent
  ]
})
export class PostModule { }
