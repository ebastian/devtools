import { Injectable, Inject } from '@angular/core';

import { Entity } from '../entity/entity';

@Injectable()
export class GenericService {

  public id: string = 'GenericService';

  protected data:Array<any> = [];

  constructor() {

  }

  public getItensFast(): Promise<Entity[]> {
    return Promise.resolve(this.data);
  }

  public getItens(): Promise<Entity[]> {
    return new Promise<Entity[]>(resolve => setTimeout(resolve, Math.floor(Math.random()*50))).then(() => this.getItensFast());
  }

  public save(item:any):void {
    console.log('genericservice save ' + JSON.stringify(item));
    if(item.id === undefined || item.id === null) {
      console.log("list: " + this.data);
      console.log("to add " + JSON.stringify(item));
      item.id = ((this.data != null && this.data.length > 0) ? (this.data[this.data.length-1].id + 1) : 1);
      this.data.push(item);
      console.log("added " + JSON.stringify(item));
    } else {
      this.getItemIndex(item.id).then(
        index => this.data[index] = item
      );
    }
  }

  public getItem(id: number): Promise<Entity> {
    console.log('getItem ' + id);
    return this.getItens().then(items => items.find(item => item.id === id));
  }

  public getItemIndex(id: number): Promise<number> {
    return this.getItens().then(items => items.findIndex(item => item.id === id));
  }

  public remove(id: number): Promise<any> {
    console.log('GenericService delete ' + id);
    return this.getItemIndex(id).then(index => this.getItens().then(items => items.splice(index, 1)));
  }
}