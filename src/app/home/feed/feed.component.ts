import { Component, Inject, OnInit } from '@angular/core';
import { Posts } from 'src/app/models/Posts';
import { UserProfile } from 'src/app/models/UserProfile';
import { PostServiceService } from 'src/app/post-service.service';
import { UserService } from 'src/app/user.service';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit{
  postsList! : Posts[];
  userProfileList! : UserProfile[];
  mergedPostList! : any;
  userProfile! : UserProfile;
  likeArray : Record<string,any> = {};
  isLikedBy: Record<number, Boolean> = {};

  constructor(public postsService : PostServiceService, private userService : UserService, private dialog: MatDialog){}

  onLike(){
    console.log("like")
    const dialogRef= this.dialog.open(LikeComponent,{
      width:'50%',
      height:'50%'
    })
  }
  onComment(postId:number){
    console.log("Comment")
    const dialogRef = this.dialog.open(CommentComponent,{
      width:'50%',
      data:{'postId':postId}
    })
  }

  onAddComment(postId:number){
    console.log("Add Comment")
    const dialogRef = this.dialog.open(AddCommentComponent,{
      width:'50%',
      data:{'postId':postId}
    })
  }
  addLike(postId: number){
    if(this.isLikedBy[postId]){
      this.likeArray[postId] = this.likeArray[postId]-1;
      this.isLikedBy[postId] = !this.isLikedBy[postId];
      this.postsService.removeLike(postId).subscribe(
        success=>{console.log(success)},
        error=>{console.log(error)}
      )
    }else{
      this.likeArray[postId] = this.likeArray[postId]+1;
      this.isLikedBy[postId] = !this.isLikedBy[postId];
      this.postsService.addLike(postId).subscribe(
        success=>{console.log(success)},
        error=>{console.log(error)}
      )
    }
  }
  ngOnInit(): void {
    this.postsList = [];
    this.postsService.getFeedPosts("0").subscribe(
      data=>{
        this.postsList=data;
        this.pollingLikeMethod();
        this.getLikeStatus();
        this.getAllUserProfile();
        console.log(this.postsList);
      },
      error=>{
        console.log(error);
      }
    )
  }

  getLikeStatus(){
    this.postsList.forEach((post)=>{
      this.postsService.isLikedBy(post.postId).subscribe(
        success=>{
          this.isLikedBy[post.postId] = success;
        },
        error=>(console.log(error))
      )
    })
  }

  pollingLikeMethod(){
    this.postsList.forEach((post)=>{
      this.postsService.pollLikeCount(post.postId,5).subscribe(
        success=>{
          this.likeArray[post.postId] = success;
        },
        error=>{console.log(error)}
      );
    })
  }

  getAllUserProfile(){
    this.userProfileList = [];
    this.postsList.forEach((post)=>{
      this.userService.getUserProfile(post.userId).subscribe(
        data=>{
          this.userProfile  = data; 
          this.userProfileList.push(this.userProfile);
          this.mergeUserProfile();
        },
        error=>{
          console.log(error);
        }
      )
    })
    console.log(this.userProfileList);
  }

  mergeUserProfile(){
    const mergedPostList = this.postsList.map((post)=>{
      const userProfile = this.userProfileList.find((profile)=> profile.userId == post.userId);
      return {...post, ...userProfile};
    })
    this.mergedPostList = mergedPostList;
    console.log(this.mergedPostList);
  }


}

@Component({
  selector: 'app-Like',
  templateUrl: './like.component.html',
  styleUrls: ['./feed.component.css']
})
export class LikeComponent implements OnInit {
  constructor(){}
  ngOnInit(): void {
      
  }
}

@Component({
  selector: 'app-Comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./feed.component.css']
})
export class CommentComponent implements OnInit {
  postId! : number;
  allComments! : any;
  constructor(private postService:PostServiceService, @Inject(MAT_DIALOG_DATA) public data: any){
    this.postId=data['postId'];
  }
  ngOnInit(): void {
    this.postService.getAllComment(this.postId).subscribe(data=>{
      console.log(data);
      this.allComments=data;
    })
  }
}

@Component({
  selector: 'app-addComment',
  templateUrl: './addcomment.component.html'
})
export class AddCommentComponent implements OnInit {

  comment!:string;
  commentForm!:FormGroup
  postId!:number;
  errorMessage!:string;
  isVisible:boolean=false;
  constructor(private formBuilder:FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any,private postService:PostServiceService, public dialogRef: MatDialogRef<AddCommentComponent>){
    console.log(data['postId']);
    this.postId=data['postId'];
  }
  
  ngOnInit(): void {
     this.commentForm=this.formBuilder.group({
      comment:[""]
    })
  }

  onComment(){
    
    let body={
      postId:this.postId,
      commentBy:localStorage.getItem('userId'),
      commentText:this.commentForm.get('comment')?.value
    }

    console.log(body);

    this.postService.addComment(body).subscribe(
      data=>{
        this.errorMessage="Comment!!"
        this.isVisible=true;
        setTimeout(() => {
          this.isVisible=false;
          this.dialogRef.close()
        }, 2500);
      },error=>{
        this.errorMessage="Something Went Wrong!!"
        this.isVisible=true;
        setTimeout(() => {
          this.isVisible=false;
        }, 2500);
        console.log("Error");
    })
    
  }
}
