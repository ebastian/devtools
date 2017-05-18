import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'eb-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  opened: boolean = false;

  @Output()
  onMenuToggle = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  toggleMenu(event): void {
    this.opened = !this.opened;
    this.onMenuToggle.next(this.opened);
  }

}
