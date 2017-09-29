import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPrivilegesTabComponent } from './user-privileges-tab.component';

describe('UserPrivilegesTabComponent', () => {
  let component: UserPrivilegesTabComponent;
  let fixture: ComponentFixture<UserPrivilegesTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserPrivilegesTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPrivilegesTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
