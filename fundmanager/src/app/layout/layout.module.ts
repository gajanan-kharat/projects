import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LeftmenuComponent } from './menu/leftmenu/leftmenu.component';
import { HeaderComponent } from './header/header.component';
import { LayoutComponent } from './layout.component';

@NgModule({
  declarations: [
    HeaderComponent,
    LeftmenuComponent,
    LayoutComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [LayoutModule]
})
export class LayoutModule { }
