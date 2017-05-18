import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'eb-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent implements OnInit {

  routePrefix: string;

  constructor(protected router:Router) {
    this.router.events.subscribe(this.routeObserve);
  }

  ngOnInit() {
  }

  routeObserve = (event) => {
    if(event instanceof NavigationEnd) {
      var i = event.url.indexOf('/', 2);
      if( i < 2) {
        i = event.url.length;
      }
      this.routePrefix = event.url.substring(1, i);
    }
  }

}