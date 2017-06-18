import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DownloadComponent } from './download.component';
import { EbAppCore } from '../shared/eb-app-core.module';

@NgModule({
  imports: [
    CommonModule,
    EbAppCore
  ],
  declarations: [DownloadComponent]
})
export class DownloadModule { }
