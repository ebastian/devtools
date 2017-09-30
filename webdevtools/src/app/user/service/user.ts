import { Entity } from '../../shared/entity/entity';

export class User extends Entity {
  
  hash: string;
  name: string;
  status: number;
  email: string;
  password: string;
  creation: Date;
  death: Date;

  constructor() { 
    super();
  };
}
