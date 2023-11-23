import { Injectable } from '@angular/core'

@Injectable({
    providedIn: 'root'
})
export class Posts{
    postId! : number;
    userId! : number;
    privacySetting! : string;
    postText! : string;
    postImage! : string;
    postVideo! : string;
    timestampColumn! : string;
    
}