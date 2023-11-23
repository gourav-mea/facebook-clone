import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendPhotosComponent } from './friend-photos.component';

describe('FriendPhotosComponent', () => {
  let component: FriendPhotosComponent;
  let fixture: ComponentFixture<FriendPhotosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FriendPhotosComponent]
    });
    fixture = TestBed.createComponent(FriendPhotosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
