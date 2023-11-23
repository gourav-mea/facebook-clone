import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserProfile } from 'src/app/models/UserProfile';
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-about-me',
  templateUrl: './about-me.component.html',
  styleUrls: ['./about-me.component.css']
})
export class AboutMeComponent implements OnInit, OnChanges{
  @Input() userProfile! : UserProfile;
  isEditing! : boolean;
  userProfileForm!: FormGroup;

  constructor(private formBuilder : FormBuilder, private userService: UserService){
    this.isEditing = false
  }

  ngOnInit(): void {
    this.userProfileForm = this.formBuilder.group({
      userId: [''],
      username: ['',[Validators.required]],
      profilePicture: ['',[Validators.required]],
      bio: ['', [Validators.required]],
      email: ['', [Validators.required]],
      dateOfBirth: ['', [Validators.required]],
      gender: ['', [Validators.required]],
      privacySetting: ['', [Validators.required]]
    })
    this.userProfileForm.disable();
  }

  ngOnChanges(changes: SimpleChanges){
    if(changes['userProfile'] && changes['userProfile'].currentValue){
      this.userProfileForm?.patchValue(changes['userProfile'].currentValue);
    }
  }

  handleUpload(event: any) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.userProfileForm.value['profilePicture'] = reader.result;
      console.log(reader.result);
    };
  }

  submitProfile(){
    this.userProfileForm.value['userId'] = localStorage.getItem('userId');
    this.userProfileForm.value['email'] = this.userProfile.email;
    console.log(this.userProfileForm.value['email']);
    this.userService.saveUserProfile(this.userProfileForm.value).subscribe(
      success=>{
        console.log(success);
        this.isEditing = false;
        this.userProfileForm.disable();
      },
      error=>{
        console.log(error);
      }
    ) 
  }

  toggleEditing(){
    this.isEditing = true;
    this.userProfileForm.get('gender')?.enable();
    this.userProfileForm.get('bio')?.enable();
    this.userProfileForm.get('dateOfBirth')?.enable();
    this.userProfileForm.get('profilePicture')?.enable();
    this.userProfileForm.get('privacySetting')?.enable();
  }

}
