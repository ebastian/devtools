import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { UserService } from '../service/user.service';
import { User } from '../service/user';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  providers: [
    UserService
  ]
})
export class UserListComponent implements OnInit {

  data: User[] = [];

  selectedRecord: User = null;

  constructor(protected service: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
  }

}
