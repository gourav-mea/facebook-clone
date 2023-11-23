import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Posts } from './models/Posts';
import { BehaviorSubject, Observable, interval, of, switchMap } from 'rxjs';
import { Like } from './models/Likes';

@Injectable({
  providedIn: 'root'
})
export class PostServiceService {
  
  constructor(private http : HttpClient) { }
  addPost(data:any):Observable<any>{
    let url="http://localhost:3000/posts/save";
    return this.http.post<any>(url,data);
  }

  addComment(data:any):Observable<String>{
    let url ="http://localhost:3000/comment/add"
    return this.http.post<String>(url,data);
  }

  getAllComment(postId:number):Observable<any>{
    let url = "http://localhost:3000/comment/getcomments?postId="+postId;
    return this.http.get<any>(url);
  }

  getFeedPosts(pageNum: string):Observable<Posts[]>{
    let url="http://localhost:3000/posts/feed?userId=" + localStorage.getItem('userId') + "&pageNum=" + pageNum;
    return this.http.get<Posts[]>(url);
  }

  getUserPosts(pageNum: string):Observable<Posts[]>{
    let url="http://localhost:3000/posts/myposts?userId=" + localStorage.getItem('userId') + "&pageNum=" + pageNum;
    return this.http.get<Posts[]>(url);
  }

  getFriendPosts(friendUserId: number, pageNum: string):Observable<Posts[]>{
    let url="http://localhost:3000/posts/friendposts/" + friendUserId + "/" + pageNum + "?userId=" + localStorage.getItem('userId');
    return this.http.get<Posts[]>(url);
  }

  addLike(postId: number):Observable<string>{
    let url="http://localhost:3000/likes/add";
    return this.http.post<string>(url, {
      postId: postId,
      likedBy: localStorage.getItem('userId'),
    })
  }

  removeLike(postId: number):Observable<string>{
    let url="http://localhost:3000/likes/unlike?likedBy=" + localStorage.getItem('userId') + "&postId=" + postId;
    return this.http.put<string>(url,{});
  }

  getLikeCount(postId:number):Observable<string>{
    let url="http://localhost:3000/likes/getcount?postId=" + postId;
    return this.http.get<string>(url);
  }

  isLikedBy(postId:number):Observable<Boolean>{
    let url="http://localhost:3000/likes/islikedby?postId=" + postId + "&userId=" + localStorage.getItem('userId');
    return this.http.get<Boolean>(url);
  }

  pollLikeCount(postId: number, intervalSeconds: number): Observable<string>{
    return interval(intervalSeconds * 1000).pipe(
      switchMap(()=> this.getLikeCount(postId))
    );
  }
  
}
