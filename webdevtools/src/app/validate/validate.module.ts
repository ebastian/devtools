import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ValidateComponent } from './validate.component';
import { EbAppCore } from '../shared/eb-app-core.module';

@NgModule({
  imports: [
    CommonModule,
    EbAppCore
  ],
  declarations: [ValidateComponent]
})
export class ValidateModule { }
