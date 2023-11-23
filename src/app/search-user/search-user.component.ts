import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, debounceTime, distinctUntilChanged, map, of, startWith, switchMap } from 'rxjs';
import { UserProfile } from '../models/UserProfile';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../user.service';

@Component({
  selector: 'app-search-user',
  templateUrl: './search-user.component.html',
  styleUrls: ['./search-user.component.css']
})
export class SearchUserComponent implements OnInit{
  searchControl = new FormControl('');
  filteredOptions!: Observable<UserProfile[]>;

  constructor(private http: HttpClient, public userService : UserService){
    this.filteredOptions = this.searchControl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(searchString => {
        if(searchString!=null && searchString?.length>0){
          return this.userService.getAllUserProfileBySearchString(searchString);
        }
        else{   
          return of([]);
        }
      }
        )
    );
  }

  ngOnInit() {}
}
