import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/user.service';
import { RegisterComponent } from '../register/register.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm!:FormGroup;
  isUserLoggedIn:boolean=false;
  errorMessage!:String;
  isVisible:boolean=false;

  constructor(private formBuilder:FormBuilder,private router: Router,private userService: UserService, public dialog:MatDialog){
    if(localStorage.getItem('isUserLoggedIn')!=null){
      this.router.navigate(['/home'])
    }else{
      this.router.navigate(['/login'])
    }
  }
  ngOnInit(): void {
      this.loginForm=this.formBuilder.group({
        username:["",Validators.required],
        password:["",Validators.required]
      })
  }

  onForgotPassword(){
    const dialogRef = this.dialog.open(ForgetPassword,{
      width:'50%',
    })
  }

  onLogin(){
    console.log(this.loginForm.value)
    this.userService.loginUser(this.loginForm.value).subscribe(
      data=>{
        this.isVisible=false;
        console.log(data);
        this.isUserLoggedIn=true;
        localStorage.setItem('isUserLoggedIn',JSON.stringify(this.isUserLoggedIn));
        localStorage.setItem('userId', data.userId);
        localStorage.setItem('username',data.username);
        console.log("Login Successful");
        this.router.navigate(['/home']);
      },
      error=>{
        this.errorMessage=error.error.errorMessage;
        this.isVisible=true;
        console.log("Something Went Wrong", error);
        setTimeout(() => {
          this.isVisible=false;
        }, 2000);
    })
  }

  openRegisterDialog(){
    const dialogRef = this.dialog.open(RegisterComponent, {
      width: '47%',
      height: '80%'
    });
  }
}


@Component({
  selector: 'app-forget',
  templateUrl: './forgetPassword.component.html',
  styleUrls: ['./forgetPassword.component.css']
})
export class ForgetPassword implements OnInit{

  forgetPasswordForm!:FormGroup
  errorMessage!:String;
  isVisible:boolean=false;

  constructor(private formBuilder:FormBuilder,private loginService:UserService,public dialogRef:MatDialogRef<ForgetPassword>){}

  ngOnInit(): void {
    this.forgetPasswordForm=this.formBuilder.group({
      username:[''],
      email:[''],
      password:[''],
      cnfpassword:['']
    })
  }
  onForgetPassword(){
    if(this.forgetPasswordForm.get('password')?.value == this.forgetPasswordForm.get('cnfpassword')?.value){
      this.loginService.forgotPassword(this.forgetPasswordForm.value).subscribe(data=>{
        this.isVisible=false;
        console.log(data)
        this.dialogRef.close();
      },error=>{
        console.log(error)
        this.isVisible=true;
        this.errorMessage=error.error.errorMessage
        setTimeout(() => {
          this.isVisible=false;
          this.forgetPasswordForm.reset;
        }, 2500);
      })
    }else{
      console.log("else")
    }
  } 
}
