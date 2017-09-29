import { Component, OnInit, AfterContentInit, Input, Output } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { UserService } from '../service/user.service';
//import { UserPrivilegeService } from '../service/user-privilege.service';
import { User } from '../service/user';
import { UserPrivilege } from '../service/user-privilege';

@Component({
  selector: 'app-user-privileges-tab',
  templateUrl: './user-privileges-tab.component.html',
  styleUrls: ['./user-privileges-tab.component.css']
})
export class UserPrivilegesTabComponent implements OnInit {

  privilege: UserPrivilege;
  
  privileges: UserPrivilege[];

  filterActives: boolean = false;

  //constructor(protected service: ComponentVersionService, private route: ActivatedRoute, private router: Router) {
  constructor() {
    this.privileges = new Array<UserPrivilege>();

    this.privilege = new UserPrivilege();
    this.privilege.className = 'Classe da funcionalidade 1';
    this.privilege.description = 'Descrição da funcionalidade 1';
    this.privileges.push(this.privilege);
    
    this.privilege = new UserPrivilege();
    this.privilege.className = 'Classe da funcionalidade 2';
    this.privilege.description = 'Descrição da funcionalidade 2';
    this.privileges.push(this.privilege);

    this.privilege = null;
  }

  ngOnInit() {
  }

   ngAfterContentInit():void {
    
  }

}
