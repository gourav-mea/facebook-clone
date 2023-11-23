import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Posts } from 'src/app/models/Posts';
import { UserProfile } from 'src/app/models/UserProfile';
import { PostServiceService } from 'src/app/post-service.service';

@Component({
  selector: 'app-user-photos',
  templateUrl: './user-photos.component.html',
  styleUrls: ['./user-photos.component.css']
})
export class UserPhotosComponent implements OnInit{
  @Input() userProfile! : UserProfile;
  myPostList!: Posts[];
  likeArray : Record<string,any> = {};
  isLikedBy: Record<number, Boolean> = {};

  constructor(private postService : PostServiceService, private router: Router){}

  ngOnInit(): void {
    this.postService.getUserPosts("0").subscribe(
      success=>{
        this.myPostList = success;
        this.pollingLikeMethod();
        this.getLikeStatus();
        console.log(success);
    },
      error=>(console.log(error))
    )
  }

  addLike(postId: number){
    if(this.isLikedBy[postId]){
      this.likeArray[postId] = this.likeArray[postId]-1;
      this.isLikedBy[postId] = !this.isLikedBy[postId];
      this.postService.removeLike(postId).subscribe(
        success=>{console.log(success)},
        error=>{console.log(error)}
      )
    }else{
      this.likeArray[postId] = this.likeArray[postId]+1;
      this.isLikedBy[postId] = !this.isLikedBy[postId];
      this.postService.addLike(postId).subscribe(
        success=>{console.log(success)},
        error=>{console.log(error)}
      )
    }
  }

  getLikeStatus(){
    this.myPostList.forEach((post)=>{
      this.postService.isLikedBy(post.postId).subscribe(
        success=>{
          this.isLikedBy[post.postId] = success;
        },
        error=>(console.log(error))
      )
    })
  }

  pollingLikeMethod(){
    this.myPostList.forEach((post)=>{
      this.postService.pollLikeCount(post.postId,5).subscribe(
        success=>{
          this.likeArray[post.postId] = success;
        },
        error=>{console.log(error)}
      );
    })
  }

  navigateToHome(){
    this.router.navigate(['/home']);
  }

}
