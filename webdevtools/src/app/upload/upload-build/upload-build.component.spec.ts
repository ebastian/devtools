import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadBuildComponent } from './upload-build.component';

describe('UploadBuildComponent', () => {
  let component: UploadBuildComponent;
  let fixture: ComponentFixture<UploadBuildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadBuildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadBuildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
