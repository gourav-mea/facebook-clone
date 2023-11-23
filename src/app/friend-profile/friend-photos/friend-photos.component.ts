import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Posts } from 'src/app/models/Posts';
import { UserProfile } from 'src/app/models/UserProfile';
import { PostServiceService } from 'src/app/post-service.service';

@Component({
  selector: 'app-friend-photos',
  templateUrl: './friend-photos.component.html',
  styleUrls: ['./friend-photos.component.css']
})
export class FriendPhotosComponent {
  @Input() friendProfile! : UserProfile;
  friendPostList!: Posts[];
  friendUserId!: number;
  likeArray : Record<string,any> = {};
  isLikedBy: Record<number, Boolean> = {};

  constructor(private postService : PostServiceService, private router: Router, private route: ActivatedRoute){}

  ngOnInit(): void {
    this.route.params.subscribe(
      param=>(this.friendUserId=param['id'])
    )
    this.postService.getFriendPosts(this.friendUserId ,"0").subscribe(
      success=>{
        this.friendPostList = success;
        this.pollingLikeMethod();
        this.getLikeStatus();
        console.log(success);
    },
      error=>(console.log(error))
    )
  }

  getLikeStatus(){
    this.friendPostList.forEach((post)=>{
      this.postService.isLikedBy(post.postId).subscribe(
        success=>{
          this.isLikedBy[post.postId] = success;
        },
        error=>(console.log(error))
      )
    })
  }

  pollingLikeMethod(){
    this.friendPostList.forEach((post)=>{
      this.postService.pollLikeCount(post.postId,5).subscribe(
        success=>{
          this.likeArray[post.postId] = success;
        },
        error=>{console.log(error)}
      );
    })
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
}
