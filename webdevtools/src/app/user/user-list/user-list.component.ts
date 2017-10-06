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

  ngOnInit(): void {
  }

  ngAfterContentInit():void {
    this.getUsers();
  }

  getUsers = (): Promise<User[]> => this.service.getItens().then(data => this.data = (data !== null ? data as User[] : new Array<User>()));
  selectRecord = (record): User => this.selectedRecord = record;
  search = (): void => alert('Não implementado.');
  
  clickEdit = (event: Event, record: User) => { 
    console.log("click edit: " + event);
    event.stopImmediatePropagation(); 
    this.openRecord(record) 
  };
  
  clickDelete = (event: Event, record: User) => { 
    console.log("click delete: " + event);
    event.stopImmediatePropagation(); 
    this.service.remove(record.id).then(
      () => this.getUsers()
    )
  };

  openRecord(record): void {
    console.log(JSON.stringify(record));
    this.selectRecord(record);
    this.router.navigate(["usuarios/usuario/", this.selectedRecord.id]);
  }

}