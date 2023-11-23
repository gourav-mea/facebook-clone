import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ForgetPassword, LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog'

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ForgetPassword
  ],
  imports: [
    ReactiveFormsModule,
    CommonModule,
    MatDialogModule
  ],
  exports: [
    LoginComponent,
    RegisterComponent
  ]
})
export class UserModule { }
