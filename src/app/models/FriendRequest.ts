import { Injectable } from '@angular/core'

@Injectable({
    providedIn: 'root'
})
export class FriendRequest{
    id!: number;
    sentTo!: number;
    sentFrom!: number;
    requestStatus!: string;
}