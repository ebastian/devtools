import { Component, OnInit, AfterContentInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { User } from './service/user';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  title: string = 'Usuários';

  user: User = new User();

  passwordCheck: string = '';
  
  constructor() { }

  ngOnInit() {
  }

}
