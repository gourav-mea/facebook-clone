import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserService } from 'src/app/user.service';
import { DateValidator } from './DateValidator';
import { ConfirmPasswordValidator } from './ConfirmPasswordValidator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registrationForm!: FormGroup;
  errorMessage!:String;
  isVisible:boolean=false;
  constructor(private formBuilder: FormBuilder, private router: Router, private faceBookLogin: UserService, public dialogRef: MatDialogRef<RegisterComponent>) {

  }
  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      username: ["", [Validators.required, Validators.pattern("([A-Z][A-Za-z0-9]*)( [A-Z][A-Za-z0-9]*)+")]],
      email: ["", [Validators.required, Validators.pattern("^[a-zA-Z0-9_\.]+@[a-zA-Z0-9-]+\.(com|org|in)$")]],
      dateOfBirth: ["", [Validators.required, DateValidator.checkDate]],
      gender: ["", Validators.required],
      password: ["", [Validators.required, Validators.pattern(".*[A-Z].*"), Validators.pattern(".*[a-z].*"), Validators.pattern(".*[0-9].*"), Validators.pattern(".*[^A-Za-z0-9].*"), Validators.pattern(".{8,16}")]],
      confirmPassword: ["", Validators.required]
    });
  }

  checkConfirmPassword(){
    if(this.registrationForm.get('password')?.value!=this.registrationForm.get('confirmPassword')?.value){
      return true;
    }else{
      return false;
    }
  }

  onRegistration() {
    this.faceBookLogin.registerUser(this.registrationForm.value).subscribe(success => {
      console.log(success);
      this.isVisible=true;
      this.errorMessage="Registration Successfully!!"
      localStorage.setItem('isUserLoggedIn',JSON.stringify(true));
      localStorage.setItem('userId', success.userId);
      localStorage.setItem('username',success.username);
      setTimeout(() => {
        this.isVisible=false;
        this.dialogRef.close();
        this.router.navigate(['/home'])
      }, 500);
      
    }, error => {
      this.errorMessage=error.error.errorMessage;
      this.isVisible=true;
      console.log("Something Went Wrong", error);
      setTimeout(() => {
        this.isVisible=false;
      }, 2000);
    })
  }

  
}
