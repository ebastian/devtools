import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UploadComponent } from './upload.component';
import { UploadBuildComponent } from './upload-build/upload-build.component';
import { UploadDocumentComponent } from './upload-document/upload-document.component';
import { UploadAppComponent } from './upload-app/upload-app.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ],
  declarations: [ UploadComponent, UploadBuildComponent, UploadDocumentComponent, UploadAppComponent ],
  exports: [ UploadComponent ]
})
export class UploadModule { }
