import { Injectable } from '@angular/core'

@Injectable({
    providedIn: 'root'
})
export class UserProfile{
    profileId! : number;
    userId! : number;
    privacySetting! : string;
    username! : string;
    profilePicture! : string;
    bio! : string;
    email! : string;
    dateOfBirth! : string;
    gender! : string; 
}