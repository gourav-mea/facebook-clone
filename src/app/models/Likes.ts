import { Injectable } from '@angular/core'

@Injectable({
    providedIn: 'root'
})
export class Like{
    likeId!: number;
    postId! : number;
    likedBy! : number;
    timestampColumn! : string;
}