import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { UserService } from '../service/user.service';
import { User } from '../service/user';

import { UserPrivilegesTabComponent } from '../user-privileges-tab/user-privileges-tab.component';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
  providers: [
    UserService
  ]
})
export class UserFormComponent implements OnInit {

  selectedTab: string;

  user: User = new User();

  busy: boolean = false;
  saveAndContinue: boolean = false;

  passwordCheck: string = undefined;

  constructor(protected service: UserService, private route: ActivatedRoute, private router: Router) { 
  }

  ngOnInit() {
  }

  ngAfterContentInit(): void {
    this.route.params.forEach((params: Params) => {
      let id = +params['id'];
      if (!isNaN(id)) {
        this.loadUser(id);
      } else {
        this.setBusy(false);
      }
    });
  }

  loadUser = (id: number) => this.service.getUser(id).then(this.assignUser).then(this.logUser).then(this.unBusy).then(this.openUsers);
  assignUser = userLoaded => this.user = (userLoaded !== null && userLoaded !== undefined ? userLoaded as User : new User());
  logUser = () => console.log("User loaded: " + JSON.stringify(this.user));
  openUsers = () => this.user.id !== undefined ? this.selectTab('usuario') : '';

  selectTab = (abaId: string): string => this.selectedTab = abaId;

  setBusy = (busy: boolean) => this.busy = busy;
  unBusy = () => this.setBusy(false);

  clickSave = (user: User): void => {
    this.service.save(user).then(this.saveResponse);
  };

  saveResponse = (comp:User) => {
    this.saveAndContinue ? this.assignUser(comp) : this.showUsersList();
  }

  clickDelete = (user: User): void => { this.service.remove(user.id); this.showUsersList(); };
  clickClear = () => this.user = new User();
  clickToggleActive = (user: User): void => {
    if(user.death === null || user.death === undefined) {
      this.service.kill(user.id).then(this.toggleActiveResponse('kill'));
    } else {
      this.service.revive(user.id).then(this.toggleActiveResponse('revive'));
    }
  };

  toggleActiveResponse = action => (success: boolean): void => {
      console.log(action + ' -> Ok? ' + success);
      this.loadUser(this.user.id);
  }

  showUsersList = (): Promise<boolean> => this.router.navigate(["./usuario"]);

}