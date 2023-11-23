import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutFriendComponent } from './about-friend.component';

describe('AboutFriendComponent', () => {
  let component: AboutFriendComponent;
  let fixture: ComponentFixture<AboutFriendComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AboutFriendComponent]
    });
    fixture = TestBed.createComponent(AboutFriendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
