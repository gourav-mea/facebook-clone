import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserProfile } from 'src/app/models/UserProfile';
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-about-friend',
  templateUrl: './about-friend.component.html',
  styleUrls: ['./about-friend.component.css']
})
export class AboutFriendComponent implements OnInit{
  @Input() friendProfile! : UserProfile;
  isEditing! : boolean;
  friendProfileForm!: FormGroup;

  constructor(private formBuilder : FormBuilder, private userService: UserService){
    this.isEditing = false
  }

  ngOnInit(): void {
    this.friendProfileForm = this.formBuilder.group({
      userId: [''],
      username: ['',[Validators.required]],
      profilePicture: ['',[Validators.required]],
      bio: ['', [Validators.required]],
      email: ['', [Validators.required]],
      dateOfBirth: ['', [Validators.required]],
      gender: ['', [Validators.required]],
      privacySetting: ['', [Validators.required]]
    })
    this.friendProfileForm.disable();
  }

  ngOnChanges(changes: SimpleChanges){
    if(changes['friendProfile'] && changes['friendProfile'].currentValue){
      this.friendProfileForm.patchValue(changes['friendProfile'].currentValue);
    }
  }

  handleUpload(event: any) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.friendProfileForm.value['profilePicture'] = reader.result;
      console.log(reader.result);
    };
  }

  submitProfile(){
    this.friendProfileForm.value['userId'] = localStorage.getItem('userId');
    this.friendProfileForm.value['email'] = this.friendProfile.email;
    console.log(this.friendProfileForm.value['email']);
    this.userService.saveUserProfile(this.friendProfileForm.value).subscribe(
      success=>{
        console.log(success);
        this.isEditing = false;
        this.friendProfileForm.disable();
      },
      error=>{
        console.log(error);
      }
    ) 
  }
}
