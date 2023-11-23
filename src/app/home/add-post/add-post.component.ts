import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { PostServiceService } from 'src/app/post-service.service';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent {
  constructor(public dialog : MatDialog){}
  openAddPostDialog(){
    const dialogRef = this.dialog.open(AddPostDialog,{
      width: '40%'
    });
    console.log("hii")
  }
}

@Component({
  selector: 'app-postdialog',
  templateUrl: './add-post-dialog.component.html',
  styleUrls: ['./add-post-dialog.component.css']
})
export class AddPostDialog implements OnInit{
  addPost!:FormGroup;
  errorMessage!:String;
  isVisible:boolean=false;

  constructor(private formBuilder:FormBuilder,private postService:PostServiceService, public dialogRef:MatDialogRef<AddPostDialog>){}
  
  ngOnInit(): void {
    this.addPost=this.formBuilder.group({
      userId:[''],
      postText:[''],
      postImage:['',Validators.required],
      privacySetting:['',Validators.required]
    })
  }
  image! : string | ArrayBuffer | null;
  handleUpload(event: any):any {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.image = reader.result;
      console.log(reader.result);
      return reader.result;
    };
  }

  onPost(){

    let data={
      userId:localStorage.getItem('userId'),
      postText:this.addPost.get('postText')?.value,
      privacySetting:this.addPost.get('privacySetting')?.value,
      postImage:this.image
    }

    this.postService.addPost(data).subscribe(data=>{
      this.errorMessage=data;
      this.isVisible=true;
      setTimeout(() => {
        this.isVisible=false;
        this.addPost.reset;
      }, 2500);
      console.log(data);
      this.dialogRef.close();
    },error=>{
      this.errorMessage="Some thing Went Wrong!";
      this.isVisible=true;
      setTimeout(() => {
        this.isVisible=false;
      }, 2500);
    })
  }
}